import { Typography, Avatar, Grid, makeStyles, Box, Card, CardContent, Button, TextField } from '@material-ui/core';
import React, { useEffect, useState } from 'react'
import agent from "../../agent";
import { forEach } from "react-bootstrap/ElementChildren";


function getUserData(s) {
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
        modify: modify === "null" ? "" : modify,
        userName: userName === "null" ? "" : userName,
        phone: phone === "null" ? "" : phone,
        realName: realName === "null" ? "" : realName,
        clazz: clazz === "null" ? "" : clazz,
        sno: sno === "null" ? "" : sno,
        dormitory: dormitory === "null" ? "" : dormitory,
        gender: gender === "null" ? "" : gender,
        createTime: createTime === "null" ? "" : createTime,
        avatar: avatar === "null" ? "" : avatar
    };
}

const useStyles = makeStyles((theme) => ({
    root: {
        backgroundColor: '#F5F5F5',
        padding: theme.spacing(12)
    },
    title: {
        fontWeight: 'bold',
        textAlign: 'center',
        padding: theme.spacing(2)
    },
    avatar: {
        width: theme.spacing(20),
        height: theme.spacing(20),
        margin: 'auto'
    },
    info: {
        border: '1px solid #ccc',
        padding: theme.spacing(2),
        margin: theme.spacing(2),
        borderRadius: theme.spacing(1)
    }
}));

function Profile() {
    const classes = useStyles();
    const [userInfo, setUserInfo] = useState({});
    const [editing, setEditing] = useState(false);
    const fetchData = async () => {
        const [info] = await Promise.all([

            agent.Profile.getUserInfo()

        ]);
        console.log(info.msg)
        if (info.msg != '1') {

        }
        setUserInfo(getUserData(info.msg))
        console.log(userInfo)
    };

    useEffect(() => {
        fetchData();
    }, []);

    function handleUserInfoChange(event, clazz) {
        setUserInfo(prevUserInfo => ({
            ...prevUserInfo,
            [clazz]: event.target.value
        }));
    }

    async function handleSave() {
        if (navigator.onLine) {
            Promise.all([
                agent.Profile.updateAvatar(userInfo.avatar),
                agent.Profile.updateSno(userInfo.sno),
                agent.Profile.updateName(userInfo.realName),
                agent.Profile.updateGender(userInfo.gender),
                agent.Profile.updateClazz(userInfo.clazz),
                agent.Profile.updateDormitory(userInfo.dormitory)
            ]).then(results => {
                const allSucceeded = results.every(result => result.result === 1);
                if (allSucceeded) {
                    alert("个人信息更新成功！");
                } else {
                    alert("个人信息更新失败！");
                }
                setEditing(false)
            }).catch(error => {
                alert("个人信息更新失败！");
            });
        } else {
            alert("网络连接异常，请检查网络设置！");
        }

    }

    return (
        <Box className={classes.root}>
            <Grid container spacing={3}>
                <Grid item xs={12} style={{textAlign:'center', spacing: 26, display: 'block' }} >
                    {editing ?
                        <>
                            <TextField label="头像" value={userInfo.avatar}
                                onChange={(event) => handleUserInfoChange(event, "avatar")} />
                            <TextField label="学号" value={userInfo.realName}
                                onChange={(event) => handleUserInfoChange(event, "realName")} />
                        </>
                        :
                        <>
                            <Avatar alt={userInfo.realName} src={userInfo.avatar} className={classes.avatar} />
                            <Typography variant="h4" className={classes.title}>
                                {userInfo.realName}
                            </Typography>
                        </>
                    }
                </Grid>
                <Grid item xs={12} >
                    <Card className={classes.info}>
               
                            <Typography variant="h6">个人信息</Typography>
                            {editing ?
                                <>
                                    <TextField label="班级" value={userInfo.clazz}
                                        onChange={(event) => handleUserInfoChange(event, "clazz")} />
                                    <TextField label="学号" value={userInfo.sno}
                                        onChange={(event) => handleUserInfoChange(event, "sno")} />
                                    <TextField label="性别" value={userInfo.gender}
                                        onChange={(event) => handleUserInfoChange(event, "gender")} />
                                </>
                                :
                                <>
                                    <Typography variant="subtitle1">班级：{userInfo.clazz}</Typography>
                                    <Typography variant="subtitle1">学号：{userInfo.sno}</Typography>
                                    <Typography variant="subtitle1">性别：{userInfo.gender}</Typography>
                                </>
                            }
            
                    </Card>
                    <Card className={classes.info}>
        
                            <Typography variant="h6">联系方式</Typography>
                            {editing ?
                                <>
                                    <TextField label="用户名" value={userInfo.userName}
                                        onChange={(event) => handleUserInfoChange(event, "userName")} />
                                    <TextField label="电话" value={userInfo.phone}
                                        onChange={(event) => handleUserInfoChange(event, "phone")} />
                                    <TextField label="宿舍" value={userInfo.dormitory}
                                        onChange={(event) => handleUserInfoChange(event, "dormitory")} />
                                </>
                                :
                                <>
                                    <Typography variant="body1">用户名：{userInfo.userName}</Typography>
                                    <Typography variant="body1">电话：{userInfo.phone}</Typography>
                                    <Typography variant="body1">宿舍：{userInfo.dormitory}</Typography>
                                </>
                            }
              
                    </Card>
                    <Box mt={2} style={{ padding: '10px', margin: '10px', alignContent: 'center' }} >

                        {editing ?
                            <Button variant="contained" color="primary" onClick={handleSave}>保存</Button>
                            :
                            <Button variant="contained" onClick={() => setEditing(true)}>编辑个人数据</Button>
                        }


                    </Box>
                </Grid>
            </Grid>
        </Box>
    );
}

export default Profile

