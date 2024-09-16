function openNav() {
    document.getElementById('mySidebar').style.width = "250px";
}

function openFormAdd(){
    document.getElementById('friendSidebar').style.width = "250px";
}

function closeFormAdd(){
    document.getElementById('friendSidebar').style.width = "0";
}

function closeNav() {
    document.getElementById('mySidebar').style.width = "0";
}

function openNav2() {
    document.getElementById('mySidebar2').style.width = "250px";
}

function closeNav2() {
    document.getElementById('mySidebar2').style.width = "0";
}

function updateProcessBar(id,name){
    const form = document.querySelector('[name='+name+']');
    const process = document.querySelector('#' +id);

    const nodes = form.querySelectorAll('input');
    let width = 0;
    for(let x of nodes){
        if(x.checkValidity()){
            width += Math.round(100/nodes.length);
        }
    }
    if(width === 0){
        width = 10;
    }
    if(width === 99) width = 100;
    process.style.width = width+'%';
    process.innerHTML = width + '%';
}

function notANumber(inp){
    inp.value = inp.value.replace(/[^0-9]/,'')
}

function autoInsertValue(room){
    document.querySelector('#input-room-login').value = room;
}

function removeRequest(){
    let userid = document.getElementById('sidebar-user-id').value;
    let friendid = document.getElementById('sidebar-friend-id').value;
    let friendname = document.getElementById('sidebar-friend-name').value;
    const request = new XMLHttpRequest();
    request["onload"] = function () {
        let parse = JSON.parse(this.responseText);
        if(parse == null) return;
        alertBox(parse);
        removeFriend(friendid);
    }
    request.open('POST',"/index/friend/remove");
    request.setRequestHeader('Content-type','application/json;charset=UTF-8;');
    request.send(JSON.stringify({userid:""+userid,friendid:""+friendid,friendname:""+friendname}))
}

function addRequest(){
    let userid = document.getElementById('form-user-id').value;
    let friendid = document.getElementById('form-friend-id').value;
    let friendname = document.getElementById('form-friend-friendname').value;
    const request = new XMLHttpRequest();
    request["onload"] = function () {
        let parse = JSON.parse(this.responseText);
        if(parse == null) return;
        alertBox(parse);
        displayFriend(parse.raw,userid,friendid,friendname);
    }
    request.open('POST',"/index/friend/add");
    request.setRequestHeader('Content-type','application/json;charset=UTF-8;');
    request.send(JSON.stringify({userid:""+userid,friendid:""+friendid,friendname:""+friendname}))
}

function alertBox(text){
    document.getElementById('alert-place').innerHTML =
        '<div class="alert info"><span class="closebtn">&times;</span>'
        + text.raw + '</div>'
    setTimeout(function (){
        document.getElementById('alert-place').innerHTML = '';
    },5000);
}

function removeFriend(friendid) {
    document.getElementById('friend-section').querySelector('#' + friendid).remove();
    decrease('friend-count', 1);
}

const message1 = "successful";
function displayFriend(raw,userid,friendid,friendname){
    let section = document.getElementById('friend-section');
    if(raw.substring(raw.length-11,raw.length-1) === message1){
        section.innerHTML += '<div id="'+friendid+'"><div>'+friendid+'</div><div>'+friendname+'</div>'+
            '<div><input type="hidden" value="' + userid + '" name="userid" id="sidebar-user-id">'+
            '<input type="hidden" value="' +friendid +'" name="friendid" id="sidebar-friend-id">'+
            '<input type="hidden" value="' + friendname +'" name="friendname" id="sidebar-friend-name">'+
            '<button class="card-button" onclick=removeRequest();>Remove</button></div></div>'
        inc('friend-count',1);
    }
}

setTimeout(getInvites,5000);

function getInvites(){
    const request = new XMLHttpRequest();
    request["onload"] = function (){
        displayInvites(JSON.parse(this.responseText));
    }
    let id = document.getElementById('user-id').innerText;
    let username = document.getElementById('user-username').innerText;
    request.open('POST',"/index/invites");
    request.setRequestHeader('Content-type','application/json;charset=UTF-8;');
    request.send(JSON.stringify({userID:""+id,roomName:""+username}));
}

function displayInvites(invites){
    let section = document.getElementById('invite-section');
    let count = 0;
    for(let x of invites){
        if(section.querySelector('#'+x.roomID) == null){
            section.innerHTML += '<div><div><button class="card-button" onclick=autoInsertValue(' + x.roomID + ')>' +
                'Invited to room : ' + x.roomID + '</button></div></div>'
            count++;
        }
    }
    inc('invite-count',count);
}

function inc(id,count){
    let text = document.getElementById(id).innerText;
    let number = parseInt(text)+count;
    document.getElementById(id).innerHTML = "" + number;
}

function decrease(id,count){
    let text = document.getElementById(id).innerText;
    let number = parseInt(text)-count;
    document.getElementById(id).innerHTML = "" + number;
}


