package com.example.cache;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**你实现了什么方法别人就只能用你实现了的方法
 * Created by Administrator on 2016/3/14 0014.
 */
public class MyContentProvider extends ContentProvider {
    @Override
    //创建时调用
    public boolean onCreate() {
        return false;
    }
    @Nullable
    @Override
    //根据uri查询selection匹配的记录，并却可以指定以（sortOrder）什么排序
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }
    @Nullable
    @Override
    //返回当前URi的MIMI类型，如果该URI对应的数据可能包括多条记录。那么MIMI类型字符串就是以vnd.android.dir/开头
    //如果该URI对应的数据只有一条记录，该MIMI字符串就是以vnd.android.cursor.item/开头
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    //根据uri插入values的数据；
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    //根据uri删除selection指定的记录
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    //根据uri修改selectionArgs指定的记录
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
