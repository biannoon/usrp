package com.scrcu.common.base.bo;

/**
 * 任务调度管理查询条件参数
 * @Author pengjuntao
 * @Date 2019/9/23 14:18
 * @Version 1.0
 **/
public class DataGridAscTaskSearchParas {
    private String taskId;
    private String taskName;
    private String taskCategory;
    private String nextDate;
    private String frequency;
    private String priority;
    private String nextDate_start;
    private String nextDate_end;
    private String crtDate_start;
    private String crtDate_end;
    private String state;
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(String taskCategory) {
        this.taskCategory = taskCategory;
    }

    public String getNextDate() {
        return nextDate;
    }

    public void setNextDate(String nextDate) {
        this.nextDate = nextDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getNextDate_start() {
        return nextDate_start;
    }

    public void setNextDate_start(String nextDate_start) {
        this.nextDate_start = nextDate_start;
    }

    public String getNextDate_end() {
        return nextDate_end;
    }

    public void setNextDate_end(String nextDate_end) {
        this.nextDate_end = nextDate_end;
    }

    public String getCrtDate_start() {
        return crtDate_start;
    }

    public void setCrtDate_start(String crtDate_start) {
        this.crtDate_start = crtDate_start;
    }

    public String getCrtDate_end() {
        return crtDate_end;
    }

    public void setCrtDate_end(String crtDate_end) {
        this.crtDate_end = crtDate_end;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }



}
