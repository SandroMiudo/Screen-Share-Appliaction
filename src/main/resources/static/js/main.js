function hideFriendBox(){
    document.querySelector(".flex-friend-box").style.display = 'none';
    document.querySelector("#flex-side-item-friend").innerHTML +=
        '<span class="show-item show-item-align-right animation-show-item-friend" onclick="visibleFriendBox(this)""></span>'
}

function visibleFriendBox(ele){
    ele.remove();
    document.querySelector(".flex-friend-box").style.display = 'block';
}

function hideMessageBox(){
    document.querySelector(".flex-side-item-message-box").style.display = 'none';
    document.querySelector(".flex-side-item-info-box").style.display = 'none';
    document.querySelector(".flex-side-item-message-send-box").style.display = 'none';
    document.querySelector("#flex-side-item-message-block").innerHTML +=
        '<span class="show-item animation-show-item-message" onclick="visibleMessageBox(this)""></span>'
}

function visibleMessageBox(ele){
    ele.remove();
    document.querySelector(".flex-side-item-message-box").style.display = 'block';
    document.querySelector(".flex-side-item-info-box").style.display = 'block';
    document.querySelector(".flex-side-item-message-send-box").style.display = 'block';
}

function translateMessageBoxSize(){
    let value = document.querySelector('#m-area').value;
}

function resolveCurrentDataTime(){
    const d = new Date();
    document.querySelector("#time-value").value =
        "".concat(d.getFullYear(), "-",('0'+(d.getMonth()+1)).slice(-2), "-", ('0'+d.getDate()).slice(-2), "T",
            ('0'+d.getHours()).slice(-2), ":", ('0'+d.getMinutes()).slice(-2),":",('0'+d.getSeconds()).slice(-2));
    let text = document.querySelector("#m-area").value;
    let userID = document.querySelector('#message-userID').value;
    let roomID = document.querySelector('#message-roomID').value;
    let time = document.querySelector('#time-value').value;
    let messageID = document.querySelector('#message-messageID').value;
    let roomName = document.querySelector("#message-roomName").value;
    const request = new XMLHttpRequest();
    request["onload"] = function (){

    }
    let data = JSON.stringify({rawmessage:""+text,sendtime:""+time,userID:""+userID,roomID:""
            +roomID,messageID:""+messageID,roomName:""+roomName});
    request.open('POST','/index/room/add/message');
    request.setRequestHeader('Content-type','application/json;charset=UTF-8');
    request.send(data);
    document.querySelector('#m-area').value = '';
}

function invite(friendID,roomID){
    const request = new XMLHttpRequest();
    request["onload"] = function (){
        let parse = JSON.parse(this.responseText);
        alertRoom(parse.raw);
    }
    request.open('POST',"/index/room/invite");
    request.setRequestHeader('Content-type','application/json;charset=UTF-8');
    request.send(JSON.stringify({roomID:""+roomID,friendID:""+friendID}))
}

function remove(removeID,adminID,roomID){
    const req = new XMLHttpRequest();
    req["onload"] = function (){
        let parse = JSON.parse(this.responseText);
        alertRoom(parse.raw);
    }
    req.open('POST',"/index/room/remove");
    req.setRequestHeader('Content-type','application/json;charset=UTF-8');
    req.send(JSON.stringify({removeUserID:""+removeID,adminID:""+adminID,roomID:""+roomID}))
}

function alertRoom(raw){
    document.getElementById('alert-box').innerHTML =
        '<div class="alert info"><span class="closebtn">&times;</span>'
        + raw + '</div>'
    setTimeout(function (){
        document.getElementById('alert-box').innerHTML = '';
    },3000);
}

function updateRoomStatus(){
    let text = document.querySelector('#current-room-size').innerText;
    let number = parseInt(text) - 1;
    document.querySelector('#current-room-size').innerHTML ='' +number;
    document.querySelector('#leave-button').click();
}

setInterval(gotoMainRoom,1000);


function gotoMainRoom(){
    let value = document.querySelector('#user-id-prop').value;
    let nodes = document.querySelectorAll('.flex-user-item');
    let bool = false;
    for(let x of nodes){
        if(x.id == value){
            bool = true;
        }
    }
    if(!bool){
        document.querySelector('#redirect-main-room').click();
    }
}

