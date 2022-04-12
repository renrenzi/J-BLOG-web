import axios from "axios";

export const editBlog = (condition) => {
    return axios.post('/info/editBlogInfo', condition)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}

export const pageBlog = (condition) => {
    return axios.post('/info/pageBlog', condition)
        .then(res => {
            return res.data.data;
        })
        .catch(res => {
            return false;
        })
}

export const saveBlog = (saveParam) => {
    return axios.post('/info/saveBlog2',saveParam)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
export const getBlogList = () => {
    return axios.get('/info/getAllBlog')
        .then(res => {
            return res.data.data;
        })
        .catch(res => {
            return false;
        })
}

export const getBlogInfo = (blogId) => {
    return axios.post('/info/getBlogInfo',blogId)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}

export const updateBlogStatus = (blogInfo) => {
    return axios.post('/info/blogStatus',blogInfo)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}

export const deleteBlog = (blogId) => {
    return axios.post('/info/deleteBlog',blogId)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
export const restoreBlog = (blogId) => {
    return axios.post('/info/restoreBlog',blogId)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
export const clearBlog = (blogId) => {
    return axios.post('/info/clearBlog',blogId)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
export const uploadFileByFastDFS = (file) => {
    return axios.post('/info/uploadFileByFastDFS',
                           file,
                    {'Content-Type': 'multipart/form-data'}).then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
