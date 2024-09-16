function password_visibility(element){
    if(element.type === 'password'){
        element.type = 'text';
    }
    else{
        element.type = 'password';
    }
}

function valid(element,container,message){
    if(!element.checkValidity()){
        element.style.border = 'solid 1px red';
        if(container.querySelector('span') == null){
            container.innerHTML += '<span class="span-style">' + message + '</span>'
        }
    }
    else{
        element.style.border = 'solid 1px green';
        container.querySelector(".span-style").remove();
    }
}

