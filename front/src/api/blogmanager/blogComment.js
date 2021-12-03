import axios from "axios";

export const pageComment = (condition) => {
    return axios.post('/comment/pageComment', condition)
        .then(res => {
            return res.data.data;
        })
        .catch(res => {
            return false;
        })
}
export const updateCommentStatus = (blogComment) => {
    return axios.post('/comment/commentStatus', blogComment)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
export const deleteComment = (commentId) => {
    return axios.post('/comment/deleteComment', commentId)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
export const getComment = (commentId) => {
    return axios.post('/comment/getComment', commentId)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
