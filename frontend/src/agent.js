import superagentPromise from 'superagent-promise';
import _superagent from 'superagent';

const superagent = superagentPromise(_superagent, global.Promise);

const API_ROOT = 'http://10.214.241.122:8080';

const encode = encodeURIComponent;
const responseBody = res => res.body;


const tokenPlugin = req => {
    if (localStorage.getItem('token')) {
        req.set('Authorization', `${localStorage.getItem('token')}`);
    }
}

const queue = []; // 请求队列
let isRefreshing = false; // 是否正在更新 token
let refreshPromise = null; // 更新 token 的 Promise

const handleError = err => {
	console.error(err);
  };

const requests = {
	del: url =>
	  superagent
		.del(`${API_ROOT}${url}`)
		.use(tokenPlugin)
		.then(responseBody)
		.catch(handleError),
	get: url =>
	  superagent
		.get(`${API_ROOT}${url}`)
		.use(tokenPlugin)
		.then(responseBody)
		.catch(handleError),
	put: (url, body) =>
	  superagent
		.put(`${API_ROOT}${url}`, body)
		.use(tokenPlugin)
		.then(responseBody)
		.catch(handleError),
	post: (url, body) =>
	  superagent
		.post(`${API_ROOT}${url}`, body)
		.use(tokenPlugin)
		.then(responseBody)
		.catch(handleError),
  };

const Auth = {
    login: ( password,phoneNumber) =>
        requests
			.post('/user/login', { password:password,phoneNumber:phoneNumber}),
    register: (code, password, userName, phoneNumber) =>
        requests
			.post('/user/register', { code:code,password:password,userName:userName,phoneNumber:phoneNumber }),
    sendMessage: (phoneNumber) =>
        requests.get('/code/send,${phoneNumber}'),
    changePassword: (code, newPassword)=>
        requests.post('/user/changePassword',{code:code,newPassword:newPassword}),

};

const Profile ={
    getUserInfo:()=>
        requests.get('/user/userinfo/'),
    getBuy :() =>
        requests.get('/goodwanted/get'),
    getSell :() =>
        requests.get('/good/list'),
    getCart:() =>
        requests.get('/cart/detail'),

    updateName:(realName)=>
        requests.post('/user/updaterealname',{realName: realName}),
    updateClazz:(clazz)=>
        requests.post('/user/updateclazz',{clazz:clazz}),
    updateSno:(sno)=>
        requests.post('/user/updatesno',{sno:sno}),
    updateDormitory:(dormitory)=>
        requests.post('/user/updatedormitory',{dormitory:dormitory}),
    updateGender:(gender)=>
        requests.post('/user/updategender',{gender:gender}),
    updateAvatar:(avatar)=>
        requests.post('/user/updateavatar',{avatar:avatar})
}

const Good={
    getGoodDetail: id =>
        requests.get(`/good/detail/${id}`)
}


export default {
    Auth,
    Profile,
    Good
};
