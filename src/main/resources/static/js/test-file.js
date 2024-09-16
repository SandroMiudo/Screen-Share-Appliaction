import {message_interaction,user_interaction} from "./reload.js";

class MessageData{
    rawmessage;
    sendtime;
    userID;
    roomID;
    messageID;

    constructor(rawmessage,sendtime,userID,roomID,messageID) {
        this.rawmessage = rawmessage;
        this.sendtime = sendtime;
        this.userID = userID;
        this.roomID = roomID;
        this.messageID = messageID;
    }
}

class RoomTransferData{
    userID;
    roomName;

    constructor(userID, roomName) {
        this.userID = userID;
        this.roomName = roomName;
    }
}

const content = [new MessageData("message1","2010-10-10T01:30",1,5,5),
    new MessageData("message2","2010-10-10T02:30",2,5,6),
    new MessageData("message3","2010-10-10T03:00",1,5,7)];

const content2 = [new MessageData("message5","2010-10-10T01:30",1,5,1),
    new MessageData("message6","2010-10-10T02:30",2,5,2),
    new MessageData("message7","2010-10-10T03:00",1,5,6)];

const content3 = [new RoomTransferData("1","sandro"), new RoomTransferData("3","junior"),
    new RoomTransferData("6","sandra"),new RoomTransferData("8","telmo")];

const content4 = [new RoomTransferData("10","jose"),
    new RoomTransferData("15","herald"),new RoomTransferData("1","sandro")]

runTests();

function runTests(){
    test1();
    test2();
    test3();
    test4();
}

function test1(){
    create();
    message_interaction(content,document.getElementsByClassName('flex-message-item'));
    let messageitems = document.getElementsByClassName('flex-message-item');
    let size = messageitems.length;
    console.assert(size === 7,"data ist nicht vollständig");
    console.assert(messageitems.item(size-3).id === '5' && messageitems.item(size-3).innerHTML === 'message1');
    console.assert(messageitems.item(size-2).id === '6' && messageitems.item(size-2).innerHTML === 'message2');
    console.assert(messageitems.item(size-1).id === '7' && messageitems.item(size-1).innerHTML === 'message3');
    clear();
}

function test2(){
    create();
    message_interaction(content2,document.getElementsByClassName('flex-message-item'));
    let messageitems = document.getElementsByClassName('flex-message-item');
    let size = messageitems.length;
    console.assert(size === 5);
    console.assert(messageitems.item(0).id === '1')
    console.assert(messageitems.item(1).id === '2')
    console.assert(messageitems.item(2).id === '3')
    console.assert(messageitems.item(3).id === '4')
    console.assert(messageitems.item(4).id === '6')
    clear();
}

function test3(){
    create();
    user_interaction(content3,document.getElementsByClassName('flex-user-item'));
    let user_items = document.getElementsByClassName('flex-user-item');
    console.assert(user_items.length === 4);
    console.assert(user_items.item(0).id === '1')
    console.assert(user_items.item(1).id === '3')
    console.assert(user_items.item(2).id === '6')
    console.assert(user_items.item(3).id === '8')
    clear();
}

function test4(){
    create();
    user_interaction(content4,document.getElementsByClassName('flex-user-item'));
    let user_items = document.getElementsByClassName('flex-user-item');
    console.assert(user_items.length === 3);
    console.assert(user_items.item(0).id === '1' && user_items.item(0).innerHTML === 'sandro');
    console.assert(user_items.item(1).id === '10' && user_items.item(1).innerHTML === 'jose');
    console.assert(user_items.item(2).id === '15' && user_items.item(2).innerHTML === 'herald')
    clear()
}

function create(){
    let message_box = document.getElementById('message-box');
    message_box.innerHTML += '<div class="flex-message-item" id="1"></div>';
    message_box.innerHTML += '<div class="flex-message-item" id="2"></div>';
    message_box.innerHTML += '<div class="flex-message-item" id="3"></div>';
    message_box.innerHTML += '<div class="flex-message-item" id="4"></div>';

    let user_box = document.getElementById('user-box');
    user_box.innerHTML += '<div class="flex-user-item" id="1">sandro</div>';
    user_box.innerHTML += '<div class="flex-user-item" id="3">junior</div>';
    user_box.innerHTML += '<div class="flex-user-item" id="6">sandra</div>';
    user_box.innerHTML += '<div class="flex-user-item" id="8">telmo</div>';
}

function clear(){
    const items = document.querySelectorAll(".flex-message-item");
    const user_items = document.querySelectorAll(".flex-user-item");
    for(let item of items){
        item.remove();
    }
    for(let item of user_items){
        item.remove();
    }

}