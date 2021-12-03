import axios from "axios";

export const login = (condition) => {
    return axios.post('/admin/login', condition)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}

export const getCount = () => {
    return axios.get('/admin/count')
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
export const uploadAuthorImg = (requestParam) => {
    return axios({
        url: '/admin/uploadAuthorImg',
        method: 'post',
        data: requestParam,
        headers: {'Content-Type': 'multipart/form-data'},
    }).then(res => {
        return res.data;
    }).catch(res => {
        return false;
    })
}

export const editUserInfo = (condition) => {
    return axios.post('/admin/editUserInfo', condition)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
