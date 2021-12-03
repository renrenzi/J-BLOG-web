import axios from "axios";

export const getLinkTypeList = () => {
    return axios.get('/link/getLinkTypeList')
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
export const pageLinkList = (searchParam) => {
    return axios.post('/link/pageLinkList',searchParam)
        .then(res => {
            return res.data.data;
        })
        .catch(res => {
            return false;
        })
}
export const addLink = (blogLink) => {
    return axios.post('/link/addLink',blogLink)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
export const isDel = (blogLink) => {
    return axios.post('/link/isDel',blogLink)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
export const clearLink = (blogId) => {
    return axios.post('/link/clearLink',blogId)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
export const getLink = (blogId) => {
    return axios.post('/link/getLink',blogId)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
export const editLink = (blogLink) => {
    return axios.post('/link/editLink',blogLink)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
