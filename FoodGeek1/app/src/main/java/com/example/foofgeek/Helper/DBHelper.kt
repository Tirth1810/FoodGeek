package com.example.foofgeek.Helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import com.example.foofgeek.Model.CategoryItem
import com.example.foofgeek.Model.RecipeItem
import com.example.foofgeek.Model.SearchResultItem

class DBHelper(context: Context?, name: String?, factory: CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {
    var imageHelper = ImageHelper()
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE RECIPES( _id INTEGER PRIMARY KEY AUTOINCREMENT, category TEXT,"
                    + "recipeName TEXT, author TEXT, uploardDate TEXT, howTo TEXT, description TEXT,"
                    + "thumbnail BLOB, mainImg BLOB, likeCount INTEGER);"
        )
        db.execSQL("CREATE TABLE INGREDIENTS( _id INTEGER PRIMARY KEY AUTOINCREMENT, recipeID INTEGER, ingreName TEXT);")
        db.execSQL("CREATE TABLE LIKECOUNT( _id INTEGER PRIMARY KEY AUTOINCREMENT, userID TEXT, recipeID INTEGER);")
        db.execSQL("CREATE TABLE USER( _id INTEGER PRIMARY KEY AUTOINCREMENT, userID TEXT, password TEXT);")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
    fun user_Insert(userid: String, password: String) {
        val db = writableDatabase
        db.execSQL("INSERT INTO USER VALUES(null, '$userid', '$password');")
        db.close()
    }

    fun user_IsUsernameFree(userId: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM USER WHERE userID = '$userId'", null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                return false
            }
        }
        cursor!!.close()
        db.close()
        return true
    }

    fun user_Login(userId: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM USER WHERE userID = '$userId' AND password = '$password'",
            null
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                return true
            }
        }
        cursor!!.close()
        db.close()
        return false
    }

    fun user_Allcount(): Int {
        val db = readableDatabase
        var count = 0
        val cursor = db.rawQuery("SELECT COUNT(*) FROM USER", null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                count = cursor.getInt(0)
            }
        }
        cursor!!.close()
        db.close()
        return count
    }

    fun recipes_Insert(
        category: String?, recipeName: String?, author: String?,
        uploardDate: String?, howTo: String?, description: String?,
        thumbnail: ByteArray?, mainImg: ByteArray?, likeCount: Int
    ) {
        val db = writableDatabase
        val p = db.compileStatement("INSERT INTO recipes values(?,?,?,?,?,?,?,?,?,?);")
        p.bindNull(1)
        p.bindString(2, category)
        p.bindString(3, recipeName)
        p.bindString(4, author)
        p.bindString(5, uploardDate)
        p.bindString(6, howTo)
        p.bindString(7, description)
        p.bindBlob(8, thumbnail)
        p.bindBlob(9, mainImg)
        p.bindLong(10, likeCount.toLong())
        p.execute()
        db.close()
    }

    fun ingredients_Insert(recipeid: Int, ingreName: String) {
        val db = writableDatabase
        db.execSQL("INSERT INTO INGREDIENTS VALUES(null, $recipeid, '$ingreName');")
        db.close()
    }

    fun ingredients_SelectByRecipeId(id: Int): ArrayList<String> {
        val db = readableDatabase
        val ingredients = ArrayList<String>()
        val cursor = db.rawQuery("SELECT ingreName FROM INGREDIENTS WHERE recipeID = $id", null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ingredients.add(cursor.getString(0))
            }
        }
        cursor!!.close()
        db.close()
        return ingredients
    }

    fun ingredients_selectRecipeByIngredientName(ingredientsName: ArrayList<String>): ArrayList<SearchResultItem> {
        val db = readableDatabase
        val idRecipes = ArrayList<SearchResultItem>()
        var strNames = ""
        for (i in ingredientsName.indices) {
            strNames += "ingreName = '" + ingredientsName[i] + "'"
            if (i != ingredientsName.size - 1) {
                strNames += " OR "
            }
        }
        val cursor = db.rawQuery(
            "SELECT recipeID, count(*) FROM INGREDIENTS WHERE " +
                    strNames + " GROUP BY recipeID", null
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                idRecipes.add(SearchResultItem(cursor.getInt(0), cursor.getInt(1)))
            }
        }
        cursor!!.close()
        db.close()
        return idRecipes
    }

    fun ingredients_selectIdRecipeByIngredientName(ingredientsName: ArrayList<String>): ArrayList<Int> {
        val db = readableDatabase
        val idRecipes = ArrayList<Int>()
        for (i in ingredientsName.indices) {
            val cursor = db.rawQuery(
                "SELECT recipeID FROM INGREDIENTS WHERE ingreName = '" + ingredientsName[1] + "'",
                null
            )
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    idRecipes.add(cursor.getInt(0))
                }
            }
            cursor!!.close()
        }
        db.close()
        return idRecipes
    }

    fun countIngredientsPerRecipes(idRecipes: Int): Int {
        val db = readableDatabase
        var count = 0
        val cursor =
            db.rawQuery("SELECT COUNT(*) FROM INGREDIENTS WHERE recipeID= $idRecipes", null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                count++
            }
        }
        cursor!!.close()
        db.close()
        return count
    }

    fun recipes_GetIdByName(name: String): Int {
        val db = readableDatabase
        var id = -1
        val cursor = db.rawQuery(
            "SELECT _id FROM RECIPES WHERE recipeName = '$name' ORDER BY _id DESC LIMIT 1",
            null
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                id = cursor.getInt(0)
            }
        }
        cursor!!.close()
        db.close()
        return id
    }

    fun recipes_SelectNew(): ArrayList<RecipeItem> {
        val db = readableDatabase
        val allRecipes = ArrayList<RecipeItem>()
        val cursor = db.rawQuery("SELECT * FROM RECIPES ORDER BY _id DESC LIMIT 3", null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                allRecipes.add(
                    RecipeItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getBlob(7),
                        cursor.getBlob(8),
                        cursor.getInt(9)
                    )
                )
            }
        }
        cursor!!.close()
        db.close()
        return allRecipes
    }

    fun recipes_SelectCategory(): ArrayList<CategoryItem> {
        val db = readableDatabase
        val categoryList = ArrayList<CategoryItem>()
        val cursor = db.rawQuery(
            "SELECT category, mainImg FROM RECIPES GROUP BY category HAVING max(_id)",
            null
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                categoryList.add(
                    CategoryItem(
                        cursor.getString(0),
                        cursor.getBlob(1)
                    )
                )
            }
        }
        cursor!!.close()
        db.close()
        return categoryList
    }

    fun recipes_SelectBest(): ArrayList<RecipeItem> {
        val db = readableDatabase
        val allRecipes = ArrayList<RecipeItem>()
        val cursor = db.rawQuery("SELECT * FROM RECIPES ORDER BY likeCount DESC LIMIT 3", null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                allRecipes.add(
                    RecipeItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getBlob(7),
                        cursor.getBlob(8),
                        cursor.getInt(9)
                    )
                )
            }
        }
        cursor!!.close()
        db.close()
        return allRecipes
    }

    fun recipes_SelectAll(): ArrayList<RecipeItem> {
        val db = readableDatabase
        val allRecipes = ArrayList<RecipeItem>()
        val cursor = db.rawQuery("SELECT * FROM RECIPES", null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                allRecipes.add(
                    RecipeItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getBlob(7),
                        cursor.getBlob(8),
                        cursor.getInt(9)
                    )
                )
            }
        }
        cursor!!.close()
        db.close()
        return allRecipes
    }

    fun recipes_SelectByCategory(category: String?): ArrayList<RecipeItem> {
        val db = readableDatabase
        val allRecipes = ArrayList<RecipeItem>()
        val cursor = db.rawQuery("SELECT * FROM RECIPES WHERE category = '$category'", null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                allRecipes.add(
                    RecipeItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getBlob(7),
                        cursor.getBlob(8),
                        cursor.getInt(9)
                    )
                )
            }
        }
        cursor!!.close()
        db.close()
        return allRecipes
    }

    fun recipes_SelectByName(name: String?): RecipeItem? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM RECIPES WHERE recipeName = '$name'", null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val recipe = RecipeItem(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getBlob(7),
                    cursor.getBlob(8),
                    cursor.getInt(9)
                )
                cursor.close()
                db.close()
                return recipe
            }
        }
        return null
    }

    fun recipes_SelectById(id: Int): RecipeItem? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM RECIPES WHERE _id = $id", null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val recipe = RecipeItem(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getBlob(7),
                    cursor.getBlob(8),
                    cursor.getInt(9)
                )
                cursor.close()
                db.close()
                return recipe
            }
        }
        return null
    }

    fun recipes_AddLike(userId: String?, recipeId: Int): Int {
        var db = readableDatabase
        var count = -1
        val cursor = db.rawQuery("SELECT likeCount FROM RECIPES WHERE _id = $recipeId", null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                count = cursor.getInt(0)
            }
        }
        cursor!!.close()
        db = writableDatabase
        db.execSQL("UPDATE RECIPES SET likeCount = " + (count + 1) + " WHERE _id = " + recipeId + ";")
        db.execSQL("INSERT INTO LIKECOUNT values(null, '$userId', $recipeId);")
        db.close()
        return count
    }

    fun recipes_MinusLike(userId: String?, recipeId: Int): Int {
        // Open available reading database
        var db = readableDatabase
        var count = -1
        val cursor = db.rawQuery("SELECT likeCount FROM RECIPES WHERE _id = $recipeId", null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                count = cursor.getInt(0)
            }
        }
        cursor!!.close()
        db = writableDatabase
        db.execSQL("UPDATE RECIPES SET likeCount = " + (count - 1) + " WHERE _id = " + recipeId + ";")
        db.execSQL("DELETE FROM LIKECOUNT WHERE userID = '$userId' AND recipeID = $recipeId;")
        db.close()
        return count
    }

    fun like_GetLikeYNByUserId(userId: String?, recipeId: Int): Boolean {
        val db = readableDatabase
        var likeNY = false
        val cursor = db.rawQuery(
            "SELECT * FROM LIKECOUNT WHERE userID = '$userId' AND recipeID = $recipeId",
            null
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                likeNY = true
            }
        }
        cursor!!.close()
        db.close()
        return likeNY
    }
}