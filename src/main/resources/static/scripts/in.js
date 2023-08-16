const eye = document.getElementById("eye")
const field = document.getElementById("password")
console.log('login form')
eye.addEventListener('click',(e)=>{
    if (field.getAttribute('type')=='text') {
        console.log('type : text')
        field.setAttribute('type','password')
    }else if(field.getAttribute('type')=='password') {
        console.log('type : password')
        field.setAttribute('type','text')
    }
})