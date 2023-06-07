const xlsx = require("node-xlsx")
const fs = require("fs")
// const db = require('./index')
const util = require('util');
const mysql = require('mysql')
const currentDate = () => {
    const formatDate = (date) => {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        const seconds = String(date.getSeconds()).padStart(2, '0');

        return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    }
    return formatDate(new Date())
}
const MappingLevel = (str) => {
    let map = new Map();
    map.set('0~6个月', 9)
    map.set('6~12个月', 8)
    map.set('1~3岁', 7)
    map.set('3~6岁', 6)
    map.set('6岁以上', 5)
    map.set('7成新及以下', 4)
    map.set('8成新', 5)
    map.set('95成新', 6)
    map.set('99成新', 7)
    map.set('9成新', 8)
    map.set('不限', 8)
    map.set('不限年龄', 8)
    map.set('产后', 6)
    map.set('全新', 10)
    map.set('无', 5)
    map.set('孕期', 5)
    map.set('暂无', 5)
    if (map.has(str)) {
        return map.get(str);
    }
    else {
        return 10;
    }
}
const MappingPrice = (str) => {
    function convertToNumber(str) {
        try {
            const match = str.match(/\d+/); // Find the first number in the string using a regular expression
            if (match) {
                if (match[0].length >= 15) {
                    return 10000000000;
                }
                return parseInt(match[0], 10); // Convert the matched number to a base-10 integer
            }
            return 100; // If no number is found, return a default value of 100
        } catch (e) {
            return 100;
        }
    }
    return convertToNumber(str);
}
const MappingSort = (str) => {
    let map = new Map();
    map.set('笔记本', 0)
    map.set('二手家具', 1)
    map.set('二手手机', 2)
    map.set('家居日用', 3)
    map.set('家用电器', 4)
    map.set('乐器/运动', 5)
    map.set('门票卡券', 6)
    map.set('母婴用品', 7)
    map.set('平板电脑', 8)
    map.set('手机配件', 9)
    map.set('数码产品', 10)
    map.set('台式电脑', 11)
    map.set('箱包服饰', 12)
    map.set('照相机', 13)
    if (map.has(str)) {
        return map.get(str);
    } else {
        return 14; // 14 表示其他
    }

}
const main = () => {

    // 读取xlsx，此处可以按照需求更改自己要读的表格
    const sheets = xlsx.parse("./test.xlsx")

    // //读取xlxs的sheet1 
    // const sheetData = sheets[0].data

    // // 建立空数组，用于放置数据
    // let testList = []

    // // testTitle也是个数组，用于读取标题行
    // testTitle = sheetData[0]
    // // for (let i = 0; i < 10; i++) {
    // //     console.log(sheets[i].data)
    // // }
    // console.log(sheets)
    // console.log(sheets[0].name)
    // console.log(sheets[0].data[1])
    // console.log(sheets[0].data[2])

    // 插入数据
    sql = `insert into good
                    (modify,
                    name,
                    level,
                    remark,
                    price,
                    sort,
                    count,
                    uid,
                    image,
                    transaction) values (${'?,'.repeat(9) + '?'})`;
    const conn = mysql.createConnection({
        host: '10.214.241.122',
        user: 'root',
        password: 'zjulss654321', // password
        database: 'zjulss'
    })
    const query = util.promisify(conn.query).bind(conn);
    (async () => {
        for (let i = 1; i < sheets[0].data.length; i++) {
            console.log(i + " " + sheets[0].data.length)
            let entry = sheets[0].data[i];
            query(sql, [
                currentDate(), entry[1], MappingLevel(entry[8]), entry[7],
                MappingPrice(entry[5]), MappingSort(entry[0]), 1, 8, entry[10], 1
            ], (err, results) => {
                if (err) {
                    throw err
                }
                else {
                    // console.log(results)
                    console.log("finish line " + i)
                    return
                }
            })
        }
    })()

    // for (let i = 1; i < sheets[0].data.length; i++) {
    //     let entry = sheets[0].data[i];

    // }
    // sheets[0].data.forEach(entry => {
    //     db.query(sql, [
    //         currentDate, entry[1], MappingLevel(entry[8]), entry[7],
    //         MappingPrice(entry[5]), MappingSort(entry[0]), 1, 8
    //     ], (err, results) => {
    //         if (err) {
    //             throw err
    //         }
    //     })
    // })


}
main()