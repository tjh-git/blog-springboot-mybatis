package com.tjh.entity;

import com.tjh.service.Impl.TagServiceImpl;
import com.tjh.service.TagService;
import com.tjh.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 博客实体类
 * Created  on 2021/3/24
 * author: tjh
 * blog
 */
public class Blog {

    private Long id;
    private String title;
    private String content;
    private String firstPicture;
    private String flag;
    private Integer views;
    private boolean appreciation;
    private boolean shareStatement;
    private boolean commentabled;
    private boolean published;
    private boolean recommend;
    private Date createTime;
    private Date updateTime;

    //这个属性用来在mybatis中进行连接查询的
    private Long typeId;
    private Long userId;

    //获取多个标签的id(和type不一样，每个blog可能有一堆标签)
    private String tagIds;
    private String description;

    private Type type;

    private User user;

    private List<Tag> tags = new ArrayList<>();

    //blog下的顶级评论
    private List<Comment> comments = new ArrayList<>();

    //初始化得到tagIds的集合，一会要用到
    public void init(){
        this.tagIds = tagsToIds(this.getTags());
        //this.tagIds =  conver(tagIds);
        //System.out.println("测试四==============================="+tagIds);
    }

    //将tags集合转换为tagIds字符串形式：“1,2,3”,用于编辑博客时显示博客的tag
    private String tagsToIds(List<Tag> tags) {

        TagService tagService = new TagServiceImpl();

        if(!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            //“1,2,博客,3”
            for(Tag tag: tags) {
                //System.out.println("tag============================Blog"+tag);
                if(flag)
                    ids.append(",");
                else
                    flag = true;
                ids.append(tag.getId());
            }
            return ids.toString();
        }
        else
            return tagIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTagIds() {


        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        //for (Tag tag : tags) {
        //    System.out.println("Blog  getTags ======="+tag);
        //}
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String conver(String tagIds){
        TagService tagService = new TagServiceImpl();
        String[] tags = tagIds.split(",");
        for(int i=0;i<tags.length;i++){
            if (!StringUtils.isNumber(tags[i])){
                tagService.saveTag(new Tag(tags[i]));
                Tag tagByName = tagService.getTagByName(tags[i]);
                tags[i] = tagByName.getId().toString();
            }
        }
        boolean flag = false;
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<tags.length;i++){
            if(flag)
                sb.append(",");
            else
                flag = true;
            sb.append(tags[i]);
        }
        System.out.println(sb.toString()+"=================================");
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", typeId=" + typeId +
                ", userId=" + userId +
                ", tagIds='" + tagIds + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", user=" + user +
                ", tags=" + tags +
                ", comments=" + comments +
                '}';
    }
}