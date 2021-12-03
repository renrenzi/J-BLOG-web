import axios from "axios";

export const getBlogTagList = () => {
    return axios.get('/tag/getBlogTag')
        .then(res => {
            return res.data.data;
        })
        .catch(res => {
            return false;
        })
}
export const pageBlogTag = (searchParam) => {
    return axios.post('/tag/pageBlogTag', searchParam)
        .then(res => {
            return res.data.data;
        })
        .catch(res => {
            return false;
        })
}
export const updateTagStatus = (blogTag) => {
    return axios.post('/tag/tagStatus', blogTag)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
export const addTag = (blogTag) => {
    return axios.post('/tag/addTag', blogTag)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
export const clearTag = (tagId) => {
    return axios.post('/tag/tagClear', tagId)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
export const getTag = (tagId) => {
    return axios.post('/tag/getTag', tagId)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
