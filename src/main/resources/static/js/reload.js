setInterval(getUsersFromServer,50);
setInterval(getMessagesFromServer,50);

function getUsersFromServer(){
    const request = new XMLHttpRequest();
    request["onload"] = function (){
        let content = JSON.parse(this.responseText);
        let elements = document.getElementsByClassName('flex-user-item');
        user_interaction(content,elements);
    };
    let roomID = document.getElementById('roomid').innerHTML;
    request.open("POST","/index/room/users");
    request.setRequestHeader('Content-type','application/json;charset=UTF-8');
    request.send(JSON.stringify({roomID:""+roomID}));
}

function user_interaction(content,elements){
    let roomAdmin = document.getElementById('room-admin-prop').value;
    let roomID = document.getElementById('room-id-prop').value;
    let bool = false;
    for(let x of content){
        for(let y of elements){
            if(x.userID == y.id){
                bool = true;
            }
        }
        if(!bool) {
            document.querySelector('.flex-item-list').innerHTML +=
                "".concat("<div class='flex-user-item' ","id='",x.userID,"'>","<div>",
                    "<div class='flex-user-item-string'>", x.roomName,"</div>",
                    "<div><button class='user-item-option'>Grant Admin</button>",
                    '<button class="user-item-option" onclick="remove(',x.id,',',roomAdmin,',',roomID,');"',">Remove User",
                "</button>","</div>","</div>","</div>");
            document.getElementById('message-box').innerHTML +=
                "".concat("<div class='flex-message-item'>","<div class='flex-message-item-general-message'>",x.roomName,
                    " joined the room.","</div>","</div>");
            document.querySelector('#current-room-size').innerHTML = ""+
                document.getElementsByClassName('flex-user-item').length;
        }
        bool = false;
    }
    let arr = Array.from(elements);
    for(let x of content){
        for(let i = 0; i < arr.length; i++){
            if(arr[i].id == x.userID){
                let temp = arr[i];
                arr[i] = arr[arr.length-1];
                arr[arr.length-1] = temp;
                arr.pop();
            }
        }
    }
    for(let element of arr){
        let roomName = document.getElementById(element.id).querySelector("div")
            .querySelector(".flex-user-item-string").innerText;
        document.getElementById(element.id).remove();
        document.getElementById('message-box').innerHTML +=
            "".concat("<div class='flex-message-item'>","<div class='flex-message-item-general-message'>",roomName,
                " left the room.","</div>","</div>");
    }
}

function message_interaction(content,elements){
    for(let x of content){
        let b = false;
        for(let y of elements){
            if(x.messageID == y.id){
                b = true;
            }
        }
        if(!b){
            let c = "".concat("<div class='flex-message-item' id='",x.messageID,"'>","<div class='flex-message-item-name'>"
                ,x.roomName,"</div>","<div class='flex-message-item-raw-message'>",x.rawmessage,"</div>","</div>");
            document.getElementById(x.messageID)
            document.getElementById('message-box').innerHTML += c;
        }
        b = false;
    }
}

function getMessagesFromServer(){
    const request = new XMLHttpRequest();
    request["onload"] = function (){
        let content = JSON.parse(this.responseText);
        let elements = document.getElementsByClassName('flex-message-item');
        message_interaction(content,elements);
    };
    let roomID = document.getElementById('roomid').innerText;
    let joinTime = document.getElementById('join-time').value;
    request.open('POST','/index/room/messages');
    request.setRequestHeader('Content-type','application/json;charset=UTF-8;')
    request.send(JSON.stringify({roomID:""+roomID,time:""+joinTime}))
}