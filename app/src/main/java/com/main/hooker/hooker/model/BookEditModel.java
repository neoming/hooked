package com.main.hooker.hooker.model;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.main.hooker.hooker.MainApplication;
import com.main.hooker.hooker.entity.Bubble;
import com.main.hooker.hooker.entity.Character;
import com.main.hooker.hooker.entity.User;
import com.main.hooker.hooker.utils.Gsoner;
import com.main.hooker.hooker.utils.Http;
import com.main.hooker.hooker.utils.State;
import com.main.hooker.hooker.utils.http.ApiFailException;
import com.main.hooker.hooker.utils.http.ApiResult;

import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by ASUS on 2018/6/10.
 * author：neoming
 */

public class BookEditModel {
    //获取权限
    public static FormBody.Builder getAuthBodyBuilder() {
        State state = MainApplication.getState();
        return new FormBody.Builder()
                .add("uid", String.valueOf(state.userGetUid()))
                .add("api_token", state.userGetApiToken());
    }

    //人物的相关操作 获取列表，增加人物，编辑已有的人物，删除人物
    public static ArrayList<Character> getCharacterList(int book_id) throws ApiFailException {
        ApiResult result = Http.get("book/character/characters" + String.valueOf(book_id));
        Gson gson = Gsoner.get();
        Type characterListType = new TypeToken<ArrayList<Character>>() {
        }.getType();
        Log.d("get characterList", "getCharacterList() returned: " + gson.fromJson(result.data, characterListType));
        return gson.fromJson(result.data, characterListType);
    }

    public static Character addCharacter(int book_id,String name,String avatar)throws ApiFailException{
        RequestBody body = getAuthBodyBuilder()
                .add("book_id",Integer.toString(book_id))
                .add("name",name)
                .add("avatar",avatar)
                .build();
        ApiResult result = Http.post("book/character/add", body);
        Gson gson = Gsoner.get();
        return gson.fromJson(result.data, Character.class);
    }

    public static Character editCharacter(int book_id,String name,String avatar)throws ApiFailException{
        RequestBody body = getAuthBodyBuilder()
                .add("book_id",Integer.toString(book_id))
                .add("name",name)
                .add("avatar",avatar)
                .build();
        ApiResult result = Http.post("book/character/edit", body);
        Gson gson = Gsoner.get();
        return gson.fromJson(result.data, Character.class);
    }

    public static void removeCharacter(int book_id,int character_id)throws ApiFailException{
        RequestBody body = getAuthBodyBuilder()
                .add("book_id",Integer.toString(book_id))
                .add("character_id",Integer.toString(character_id))
                .build();
        ApiResult result = Http.post("book/character/remove", body);
    }

    //Bubble 相关操作 增加bubble，修改bubble，删除bubble，插入bubble(注意插入操作两个函数传入值是不一样的)
    public static Bubble bubbleAdd(int book_id,int type,int position,int character_id,String content)throws ApiFailException{
        RequestBody body = getAuthBodyBuilder()
                .add("book_id",Integer.toString(book_id))
                .add("character_id",Integer.toString(character_id))
                .add("type",Integer.toString(type))
                .add("position",Integer.toString(position))
                .add("content",content)
                .build();
        ApiResult result = Http.post("book/item/add", body);
        Gson gson = Gsoner.get();
        return gson.fromJson(result.data,Bubble.class);
    }

    public static Bubble bubbleEdit(int book_id,int type,int position,int character_id,String content)throws ApiFailException{
        RequestBody body = getAuthBodyBuilder()
                .add("book_id",Integer.toString(book_id))
                .add("character_id",Integer.toString(character_id))
                .add("type",Integer.toString(type))
                .add("position",Integer.toString(position))
                .add("content",content)
                .build();
        ApiResult result = Http.post("book/item/edit", body);
        Gson gson = Gsoner.get();
        return gson.fromJson(result.data,Bubble.class);
    }

    public static void bubbleRemove(int id)throws ApiFailException{
        RequestBody body = getAuthBodyBuilder()
                .add("id",Integer.toString(id))
                .build();
        ApiResult result = Http.post("book/item/remove", body);
    }

    //向上插入一个bubble
    public static void insertUpBubble(Bubble new_bubble,int dest_id) throws ApiFailException{
        //des_id 是点击的bubble的id
        RequestBody body = getAuthBodyBuilder()
                .add("id",Integer.toString(new_bubble.id))
                .add("dest_id",Integer.toString(dest_id))
                .build();
        ApiResult result = Http.post("book/item/move", body);
    }

    //向下插入一个bubble
    public static void insertDownBubble(Bubble new_bubble,int dest_id) throws ApiFailException{
        //des_id 是点击的bubble的下一个bubble的id
        RequestBody body = getAuthBodyBuilder()
                .add("id",Integer.toString(new_bubble.id))
                .add("dest_id",Integer.toString(dest_id))
                .build();
        ApiResult result = Http.post("book/item/move", body);
    }
}
