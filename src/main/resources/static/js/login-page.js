function displayDropdown(){
    document.getElementById('dropdown-menu-create').style.display = 'block';
    wait('create-text-field',500);
    wait('create-text-field2',1000);
    wait('create-range-field',1500);
    //wait('create-invite-field',3000);
    wait('create-submit',2000);
}

function displayDropdown_Friends(){
    document.getElementById('dropdown-menu-friend').style.display = 'block';
    wait('friend-text-field',500);
    wait('friend-text-field2',1000);
    wait('submit-button-friends',1500);
}

function wait(id,dauer){
    setTimeout(()=>{
        document.getElementById(id).style.display = 'block'
    },dauer);
}

function revert_DisplayDropdown(id){
    document.getElementById(id).style.display = 'none';
}

function updateRoomSizeValue(){
    document.getElementById('value-range').innerHTML = 'Current Value : ' + document.getElementById('myRange').value;
}

let error = false;
let last_target = null;

addEventListener('dragleave',function (event){
    if(error) return;
    console.log(event.target);
    if(event.target.id !== 'drag-start-i' && event.target.id !== 'drag-start-sp' && event.target.id !== 'create-submit'
            && event.target.id !== 'drag-target'){
        error = true;
    }
    last_target = event.target.id;
});

addEventListener('dragend',function (){
    if(last_target === 'drag-target'){
        document.getElementById('submit-create').click();
    }
})

addEventListener('dragstart',function (event){
    error = false;
});