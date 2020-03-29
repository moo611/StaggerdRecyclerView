package com.atech.myapplication;

import com.atech.staggedrv.model.StaggedModel;

/**
 * 模拟数据模型
 * created by desong
 * 2020 3.29
 */
public class FakeModel implements StaggedModel {


    private int width;
    private int height;
    private int resourceId;

    public FakeModel(int width, int height, int resourceId){
        this.width = width;
        this.height = height;
        this.resourceId = resourceId;
    }



    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getThumb() {
        return null;
    }

    @Override
    public int localResorce() {
        return resourceId;
    }
}
