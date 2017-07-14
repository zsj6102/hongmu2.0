package com.colpencil.redwood.dao;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.CommentVo;
import com.colpencil.redwood.bean.MyComment;
import com.colpencil.redwood.bean.result.PostsResult;
import com.colpencil.redwood.function.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈宝
 * @Description:数据库操作工具类
 * @Email DramaScript@outlook.com
 * @date 2016/8/30
 */
public class DaoUtils {

    /**
     * 保存帖子
     */
    public static void saveNote(List<CommentVo> commentVoList, int sec_id, boolean PULL_TO_REFRESH, Context context) {
        DaoSession session = App.getDaoSession(context);
        NoteDao noteDao = session.getNoteDao();
        if (PULL_TO_REFRESH) {
            deleteById(sec_id, noteDao);
        }
        if (!ListUtils.listIsNullOrEmpty(commentVoList)) {
            for (CommentVo commentVo : commentVoList) {
                Note note = new Note();
                note.setOte_id(commentVo.getOte_id());
                note.setOte_title(commentVo.getOte_title());
                note.setOte_content(commentVo.getOte_content());
                note.setCreatetime(commentVo.getCreatetime());
                note.setSystemTime(commentVo.getSystemTime());
                note.setUrl(commentVo.getUrl());
                note.setNickname(commentVo.getNickname());
                note.setFace(commentVo.getFace());
                note.setCom_count(commentVo.getCom_count());
                note.setLike_count(commentVo.getLike_count());
                note.setSec_id(sec_id);
                noteDao.insert(note);
            }
        }
    }

    /**
     * 通过sec_id删除数据
     *
     * @param sec_id
     * @param noteDao
     */
    private static void deleteById(int sec_id, NoteDao noteDao) {
        String sql = "delete from NOTE where sec_id = " + sec_id;
        noteDao.getDatabase().execSQL(sql);
    }

    /**
     * 通过sec_id获取对应的帖子内容
     *
     * @param sec_id
     * @param context
     */
    public static List<CommentVo> getNotes(int sec_id, Context context) {
        List<CommentVo> commentVoList = new ArrayList<>();
        DaoSession session = App.getDaoSession(context);
        NoteDao noteDao = session.getNoteDao();
        List<Note> noteList = noteDao.loadAll();
        if (!ListUtils.listIsNullOrEmpty(noteList)) {
            for (Note note : noteList) {
                if (note.getSec_id() == sec_id) {
                    CommentVo comment = new CommentVo();
                    comment.setOte_id(note.getOte_id());
                    comment.setOte_title(note.getOte_title());
                    comment.setOte_content(note.getOte_content());
                    comment.setCreatetime(note.getCreatetime());
                    comment.setSystemTime(note.getSystemTime());
                    comment.setUrl(note.getUrl());
                    comment.setNickname(note.getNickname());
                    comment.setFace(note.getFace());
                    comment.setCom_count(note.getCom_count());
                    comment.setLike_count(note.getLike_count());
                    comment.setSec_id(note.getSec_id());
                    commentVoList.add(comment);
                }
            }
        }
        return commentVoList;
    }

    /**
     * 保存分类
     *
     * @param categoryVos
     * @param context
     */
    public static void saveSort(List<CategoryVo> categoryVos, Context context) {
        DaoSession daoSession = App.getDaoSession(context);
        SortVoDao sortVoDao = daoSession.getSortVoDao();
        if (!ListUtils.listIsNullOrEmpty(categoryVos)) {
            sortVoDao.deleteAll();
            for (CategoryVo categoryVo : categoryVos) {
                SortVo sortVo = new SortVo();
                sortVo.setCat_id(categoryVo.getCat_id());
                sortVo.setCat_name(categoryVo.getCat_name());
                sortVoDao.insert(sortVo);
            }
        }
        Log.e("sortVo", sortVoDao.loadAll().toString());
    }

    /**
     * 获取分类
     *
     * @param context
     * @return
     */
    public static List<CategoryVo> getSort(Context context) {
        DaoSession daoSession = App.getDaoSession(context);
        SortVoDao dao = daoSession.getSortVoDao();
        List<SortVo> list = dao.loadAll();
        List<CategoryVo> categoryVoList = new ArrayList<>();
        if (!ListUtils.listIsNullOrEmpty(list)) {
            for (SortVo vo : list) {
                CategoryVo categoryVo = new CategoryVo();
                categoryVo.setCat_id(vo.getCat_id());
                categoryVo.setCat_name(vo.getCat_name());
                categoryVoList.add(categoryVo);
            }
        }
        return categoryVoList;
    }

    /**
     * 保存我的帖子
     *
     * @param myComments
     * @param type
     * @param PULL_TO_REFRESH
     * @param context
     */
    public static void saveMyNote(List<MyComment> myComments, int type, boolean PULL_TO_REFRESH, Context context) {
        DaoSession session = App.getDaoSession(context);
        NoteDao noteDao = session.getNoteDao();
        if (PULL_TO_REFRESH) {
            deleteById(type, noteDao);
        }
        if (!ListUtils.listIsNullOrEmpty(myComments)) {
            for (MyComment comment : myComments) {
                Note note = new Note();
                note.setOte_id(comment.getOte_id());
                note.setOte_title(comment.getOte_title());
                note.setOte_content(comment.getOte_content());
                note.setCreatetime(comment.getCreatetime());
                note.setSystemTime(comment.getSystemTime());
                note.setUrl(comment.getUrl());
                note.setNickname(comment.getNickname());
                note.setFace(comment.getFace());
                note.setCom_count(comment.getCom_count());
                note.setLike_count(comment.getLike_count());
                note.setSec_id(type);
                noteDao.insert(note);
            }
        }
        Log.e("notes", noteDao.loadAll().toString());
    }

    /**
     * 获取我的帖子
     *
     * @param type
     * @param context
     * @return
     */
    public static List<MyComment> getMyNotes(int type, Context context) {
        List<MyComment> commentVoList = new ArrayList<>();
        DaoSession session = App.getDaoSession(context);
        NoteDao noteDao = session.getNoteDao();
        List<Note> noteList = noteDao.loadAll();
        if (!ListUtils.listIsNullOrEmpty(noteList)) {
            for (Note note : noteList) {
                if (note.getSec_id() == type) {
                    MyComment comment = new MyComment();
                    comment.setOte_id(note.getOte_id());
                    comment.setOte_title(note.getOte_title());
                    comment.setOte_content(note.getOte_content());
                    comment.setCreatetime(note.getCreatetime());
                    comment.setSystemTime(note.getSystemTime());
                    comment.setUrl(note.getUrl());
                    comment.setNickname(note.getNickname());
                    comment.setFace(note.getFace());
                    comment.setCom_count(note.getCom_count());
                    comment.setLike_count(note.getLike_count());
                    comment.setSec_id(note.getSec_id());
                    commentVoList.add(comment);
                }
            }
        }
        return commentVoList;
    }

    /**
     * 通过ote_id获取单个帖子
     *
     * @param ote_id
     * @param context
     * @return
     */
    public static PostsResult loadPostsById(int ote_id, Context context) {
        DaoSession daoSession = App.getDaoSession(context);
        NoteDao noteDao = daoSession.getNoteDao();
        List<Note> notes = noteDao.loadAll();
        PostsResult comment = new PostsResult();
        if (!ListUtils.listIsNullOrEmpty(notes)) {
            for (Note note : notes) {
                if (ote_id == note.getOte_id()) {
                    comment.setOte_title(note.getOte_title());
                    comment.setOte_content(note.getOte_content());
                    comment.setCreattime(note.getCreatetime());
                    comment.setSystemTime(note.getSystemTime());
                    comment.setUrl(note.getUrl());
                    comment.setNickname(note.getNickname());
                    comment.setFace(note.getFace());
                    comment.setCom_count(note.getCom_count());
                    comment.setLike_count(note.getLike_count());
                    comment.setCode("1");
                    break;
                }
            }
        }
        return comment;
    }

    /**
     * 保存搜索历史
     *
     * @param cat_id
     * @param str
     * @param context
     */
    public static void saveHistory(int cat_id, String str, Context context) {
        if (!TextUtils.isEmpty(str)) {      //搜索字段不为空的时候保存搜索字段
            DaoSession daoSession = App.getDaoSession(context);
            SearchHistoryDao dao = daoSession.getSearchHistoryDao();
            SearchHistory history = new SearchHistory();
            history.setCat_id(cat_id);
            history.setHistory(str);
            dao.insert(history);
        }
    }

    /**
     * 读取搜索历史
     *
     * @param cat_id
     * @param context
     * @return
     */
    public static List<String> getHistory(int cat_id, Context context) {
        DaoSession daoSession = App.getDaoSession(context);
        SearchHistoryDao dao = daoSession.getSearchHistoryDao();
        List<SearchHistory> list = dao.loadAll();
        List<String> mlist = new ArrayList<>();
        if (!ListUtils.listIsNullOrEmpty(list)) {
            for (SearchHistory history : list) {
                if (cat_id == history.getCat_id()) {
                    mlist.add(history.getHistory());
                }
            }
        }
        return mlist;
    }

    /**
     * 清空搜索历史
     *
     * @param cat_id
     * @param context
     */
    public static void deleteHistory(int cat_id, Context context) {
        DaoSession daoSession = App.getDaoSession(context);
        String sql = "delete from SEARCH_HISTORY where cat_id = " + cat_id;
        daoSession.getDatabase().execSQL(sql);
    }
}
