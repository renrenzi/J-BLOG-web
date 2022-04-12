import axios from "axios";

export const getCurrentLikeStatus = (searchParam) => {
    return axios.post('/front/getCurrentLikeStatus', searchParam)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}

export const likeBlog = (searchParam) => {
    return axios.post('/front/likeBlog', searchParam)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}

export const pageBlogView = (searchParam) => {
    return axios.post('/front/pageBlogView', searchParam)
        .then(res => {
            return res.data.data;
        })
        .catch(res => {
            return false;
        })
}
export const blogDetail = (searchParam) => {
    return axios.post('/front/blogDetail', searchParam)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
