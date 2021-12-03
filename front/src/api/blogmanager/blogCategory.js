import axios from "axios";

export const getBlogCategoryList = () => {
    return axios.get('/category/list')
        .then(res => {
            return res.data.data;
        })
        .catch(res => {
            return false;
        })
}
export const pageCategory = (searchParam) => {
    return axios.post('/category/pageCategory', searchParam)
        .then(res => {
            return res.data.data;
        })
        .catch(res => {
            return false;
        })
}
export const updateCategoryStatus = (category) => {
    return axios.post('/category/categoryStatus', category)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
export const addCategory = (category) => {
    return axios.post('/category/addCategory', category)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
export const clearCategory = (categoryId) => {
    return axios.post('/category/clearCategory', categoryId)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
export const getCategory = (categoryId) => {
    return axios.post('/category/getCategory', categoryId)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
