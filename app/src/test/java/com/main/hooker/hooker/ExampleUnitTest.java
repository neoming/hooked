package com.main.hooker.hooker;

import com.main.hooker.hooker.entity.Bubble;
import com.main.hooker.hooker.entity.Character;
import com.main.hooker.hooker.model.BookEditModel;
import com.main.hooker.hooker.utils.http.ApiFailException;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void addCharacter(){
        try {
            Character character = BookEditModel.addCharacter( 25,"carina", "sd");
            System.out.print(character.name+character.avatar+character.id);
        } catch (ApiFailException e){
            e.printStackTrace();
            System.out.print(e.getApiResult().msg);
        }
    }
    @Test
    public void editCharacter(){
        try {
            Character character = BookEditModel.editCharacter(23,"neoming","sdsd");
            System.out.print(character.name+character.avatar+character.id);
        } catch (ApiFailException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getCharacterList(){
        try {
            ArrayList<Character> getCharacterList = BookEditModel.getCharacterList(25);
            System.out.println(getCharacterList);
        } catch (ApiFailException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void removeCharacter(){
        try {
            BookEditModel.removeCharacter(17);
        } catch (ApiFailException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void bubbleAdd(){
        try {
            Bubble bubble = BookEditModel.bubbleAdd(2,0,1,1,"i should have 6");
            BookEditModel.insertUpBubble(bubble,12);
        } catch (ApiFailException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void bubbleEdit(){
        try {
            Bubble bubble = BookEditModel.bubbleEdit(0,1,19,"test",233);
        } catch (ApiFailException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void bubbleRemove(){
        try {
            BookEditModel.bubbleRemove(233);
        } catch (ApiFailException e) {
            e.printStackTrace();
        }
    }
}