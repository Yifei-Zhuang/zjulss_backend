import superagentPromise from 'superagent-promise';
import _superagent from 'superagent';

const superagent = superagentPromise(_superagent, global.Promise);

const API_ROOT = 'http://10.214.241.122:8080';

const encode = encodeURIComponent;
const responseBody = res => res.body;

let token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJkNGIxNDZmMS1kMTk2LTQ5NjQtYjExNi03NTUxOWZiNWU3NmEiLCJzdWIiOiI4IiwiaXNzIjoiemp1bHNzMiIsImlhdCI6MTY4MDg0MTI3MSwiZXhwIjoxNjgwODQ0ODcxfQ.EwhD6KSF1FQQbyu-7AZmmewtoQgaYZxBgTX96Cmlz0Q";

const tokenPlugin = req => {
    if (token) {
        req.set('authorization', `Token ${token}`);
    }
}

const requests = {
    del: url =>
        superagent.del(`${API_ROOT}${url}`).use(tokenPlugin).then(responseBody),
    get: url =>
        superagent.get(`${API_ROOT}${url}`).use(tokenPlugin).then(responseBody),
    put: (url, body) =>
        superagent.put(`${API_ROOT}${url}`, body).use(tokenPlugin).then(responseBody),
    post: (url, body) =>
        superagent.post(`${API_ROOT}${url}`, body).use(tokenPlugin).then(responseBody)
};

const Auth = {
    getInfo: () =>
        requests.get('/user/userinfo/8'),
    login: ( password,phoneNumber) =>
        requests.post('/user/login', { password:password,phoneNumber:phoneNumber}),
    register: (code, password, userName, phoneNumber) =>
        requests.post('/user/register', { code:code,password:password,userName:userName,phoneNumber:phoneNumber }),
    sendMessage: (phoneNumber) =>
        requests.get('/code/send,${phoneNumber}'),
    changePassword: (code, newPassword)=>
        requests.post('/user/changePassword',{code:code,newPassword:newPassword})
};

const Profile ={
    getBuy :() =>
        requests.get('/goodwanted/get'),
    getSell :() =>
        requests.get('/goodsell/get'),
    getCart:() =>
        requests.get('/cart/detail')

}




export default {
    Auth,
    Profile,
    setToken: _token => { token = _token; }
};
