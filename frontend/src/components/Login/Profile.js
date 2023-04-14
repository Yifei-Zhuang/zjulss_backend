import {Typography, Avatar, Grid, makeStyles, Box, Card, CardContent, Button, TextField} from '@material-ui/core';
import React, {useEffect, useState} from 'react'
import agent from "../../agent";


function getUserData(s){
    // 提取字符串中的属性和值
    const id = s.substring(s.indexOf("id=") + 3, s.indexOf(", modify="));
    const modify = s.substring(s.indexOf("modify=") + 7, s.indexOf(", userName="));
    const userName = s.substring(s.indexOf("userName=") + 9, s.indexOf(", phone="));
    const phone = s.substring(s.indexOf("phone=") + 6, s.indexOf(", realName="));
    const realName = s.substring(s.indexOf("realName=") + 9, s.indexOf(", clazz="));
    const clazz = s.substring(s.indexOf("clazz=") + 6, s.indexOf(", sno="));
    const sno = s.substring(s.indexOf("sno=") + 4, s.indexOf(", dormitory="));
    const dormitory = s.substring(s.indexOf("dormitory=") + 10, s.indexOf(", gender="));
    const gender = s.substring(s.indexOf("gender=") + 7, s.indexOf(", createTime="));
    const createTime = s.substring(s.indexOf("createTime=") + 11, s.indexOf(", avatar="));
    const avatar = s.substring(s.indexOf("avatar=") + 7, s.length - 1);
    return {
        id: id,
        modify: modify,
        userName: userName,
        phone: phone,
        realName: realName,
        clazz: clazz,
        sno: sno,
        dormitory: dormitory,
        gender: gender,
        createTime: createTime,
        avatar: avatar
    }
}
const useStyles = makeStyles((theme) => ({
    root: {
        backgroundColor: '#F5F5F5',
        padding: theme.spacing(2)
    },
    title: {
        fontWeight: 'bold',
        marginBottom: theme.spacing(2)
    },
    avatar: {
        width: theme.spacing(20),
        height: theme.spacing(20),
        margin: 'auto'
    },
    info: {
        border: '1px solid #ccc',
        padding: theme.spacing(2),
        borderRadius: theme.spacing(1)
    }
}));

function Profile() {
    const classes = useStyles();
    const [userInfo, setUserInfo] = useState({});
    const [editing, setEditing]=useState(false);
    const fetchData = async () => {
        const [info] = await Promise.all([

            agent.Profile.getUserInfo()

        ]);
        console.log(info.msg)
        setUserInfo(getUserData(info.msg))
        console.log(userInfo)
    };

    useEffect(() => {
        fetchData();
    }, []);

    function handleUserInfoChange(clazz) {
        
    }

    function handleSave() {
        fetchData();
    }

    return (
        <Box className={classes.root}>
            <Grid container spacing={3}>
                <Grid item xs={12}>
                    <Typography variant="h4" className={classes.title}>
                        {userInfo.realName}
                    </Typography>
                </Grid>
                <Grid item xs={3}>
                    <Avatar alt={userInfo.realName} src={userInfo.avatar} className={classes.avatar}/>
                </Grid>
                <Grid item xs={9}>
                    <Box className={classes.info}>
                        <Card>
                            <CardContent>
                                <Typography variant="h6">个人信息</Typography>
                                {editing ?
                                    <>
                                        <TextField label="班级" value={userInfo.clazz} onChange={handleUserInfoChange("clazz")} />
                                        <TextField label="学号" value={userInfo.sno} onChange={handleUserInfoChange("sno")} />
                                        <TextField label="性别" value={userInfo.gender} onChange={handleUserInfoChange("gender")} />
                                        <TextField label="ID" value={userInfo.id} onChange={handleUserInfoChange("id")} />
                                    </>
                                    :
                                    <>
                                        <Typography variant="subtitle1">班级：{userInfo.clazz}</Typography>
                                        <Typography variant="subtitle1">学号：{userInfo.sno}</Typography>
                                        <Typography variant="subtitle1">性别：{userInfo.gender}</Typography>
                                        <Typography variant="subtitle1">ID：{userInfo.id}</Typography>
                                    </>
                                }
                            </CardContent>
                        </Card>
                        <Card>
                            <CardContent>
                                <Typography variant="h6">联系方式</Typography>
                                {editing ?
                                    <>
                                        <TextField label="用户名" value={userInfo.userName} onChange={handleUserInfoChange("userName")} />
                                        <TextField label="电话" value={userInfo.phone} onChange={handleUserInfoChange("phone")} />
                                        <TextField label="宿舍" value={userInfo.dormitory} onChange={handleUserInfoChange("dormitory")} />
                                    </>
                                    :
                                    <>
                                        <Typography variant="body1">用户名：{userInfo.userName}</Typography>
                                        <Typography variant="body1">电话：{userInfo.phone}</Typography>
                                        <Typography variant="body1">宿舍：{userInfo.dormitory}</Typography>
                                    </>
                                }
                            </CardContent>
                        </Card>
                        <Typography variant="caption">加入时间：{userInfo.createTime}</Typography>
                        <Typography variant="caption">修改时间：{userInfo.modify}</Typography>
                        <Box mt={2}>
                            <Card>
                                <CardContent>
                                    {editing ?
                                        <Button variant="contained" color="primary" onClick={handleSave}>保存</Button>
                                        :
                                        <Button variant="contained" onClick={() => setEditing(true)}>编辑个人数据</Button>
                                    }
                                </CardContent>
                            </Card>
                        </Box>
                    </Box>
                </Grid>
            </Grid>
        </Box>
    );
}

export default Profile

