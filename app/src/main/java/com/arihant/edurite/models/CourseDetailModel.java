package com.arihant.edurite.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseDetailModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public class Data {

        @SerializedName("course")
        @Expose
        private Course course;
        @SerializedName("reviews")
        @Expose
        private List<Review> reviews;
        @SerializedName("materials")
        @Expose
        private List<Material> materials;

        public Course getCourse() {
            return course;
        }

        public void setCourse(Course course) {
            this.course = course;
        }

        public List<Review> getReviews() {
            return reviews;
        }

        public void setReviews(List<Review> reviews) {
            this.reviews = reviews;
        }

        public List<Material> getMaterials() {
            return materials;
        }

        public void setMaterials(List<Material> materials) {
            this.materials = materials;
        }

        public class Review {

            @SerializedName("rating_id")
            @Expose
            private String ratingId;
            @SerializedName("rating")
            @Expose
            private String rating;
            @SerializedName("username")
            @Expose
            private String username;
            @SerializedName("review")
            @Expose
            private String review;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("course_id")
            @Expose
            private String courseId;
            @SerializedName("material_id")
            @Expose
            private String materialId;
            @SerializedName("user_id")
            @Expose
            private String userId;

            public String getRatingId() {
                return ratingId;
            }

            public void setRatingId(String ratingId) {
                this.ratingId = ratingId;
            }

            public String getRating() {
                return rating;
            }

            public void setRating(String rating) {
                this.rating = rating;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getReview() {
                return review;
            }

            public void setReview(String review) {
                this.review = review;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getCourseId() {
                return courseId;
            }

            public void setCourseId(String courseId) {
                this.courseId = courseId;
            }

            public String getMaterialId() {
                return materialId;
            }

            public void setMaterialId(String materialId) {
                this.materialId = materialId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

        }

        public class Material {

            @SerializedName("material_id")
            @Expose
            private String materialId;
            @SerializedName("mat_name")
            @Expose
            private String matName;
            @SerializedName("mat_description")
            @Expose
            private String matDescription;
            @SerializedName("pdf_file")
            @Expose
            private String pdfFile;
            @SerializedName("is_paid")
            @Expose
            private String isPaid;
            @SerializedName("course_id")
            @Expose
            private String courseId;
            @SerializedName("review_count")
            @Expose
            private String reviewCount;
            @SerializedName("avg_rating")
            @Expose
            private String avgRating;

            public String getMaterialId() {
                return materialId;
            }

            public void setMaterialId(String materialId) {
                this.materialId = materialId;
            }

            public String getMatName() {
                return matName;
            }

            public void setMatName(String matName) {
                this.matName = matName;
            }

            public String getMatDescription() {
                return matDescription;
            }

            public void setMatDescription(String matDescription) {
                this.matDescription = matDescription;
            }

            public String getPdfFile() {
                return pdfFile;
            }

            public void setPdfFile(String pdfFile) {
                this.pdfFile = pdfFile;
            }

            public String getIsPaid() {
                return isPaid;
            }

            public void setIsPaid(String isPaid) {
                this.isPaid = isPaid;
            }

            public String getCourseId() {
                return courseId;
            }

            public void setCourseId(String courseId) {
                this.courseId = courseId;
            }

            public String getReviewCount() {
                return reviewCount;
            }

            public void setReviewCount(String reviewCount) {
                this.reviewCount = reviewCount;
            }

            public String getAvgRating() {
                return avgRating;
            }

            public void setAvgRating(String avgRating) {
                this.avgRating = avgRating;
            }

        }
        public class Course {

            @SerializedName("course_id")
            @Expose
            private String courseId;
            @SerializedName("cate_id")
            @Expose
            private String cateId;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("description")
            @Expose
            private String description;
            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("ispaid")
            @Expose
            private String ispaid;
            @SerializedName("video")
            @Expose
            private String video;
            @SerializedName("review_count")
            @Expose
            private String reviewCount;
            @SerializedName("avg_rating")
            @Expose
            private String avgRating;
            @SerializedName("level")
            @Expose
            private String level;

            public String getCourseId() {
                return courseId;
            }

            public void setCourseId(String courseId) {
                this.courseId = courseId;
            }

            public String getCateId() {
                return cateId;
            }

            public void setCateId(String cateId) {
                this.cateId = cateId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getIspaid() {
                return ispaid;
            }

            public void setIspaid(String ispaid) {
                this.ispaid = ispaid;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String getReviewCount() {
                return reviewCount;
            }

            public void setReviewCount(String reviewCount) {
                this.reviewCount = reviewCount;
            }

            public String getAvgRating() {
                return avgRating;
            }

            public void setAvgRating(String avgRating) {
                this.avgRating = avgRating;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

        }
    }
}
