var index = (function(){
    var url="https://"+(window.location.href).split("/")[2];
    function acceso(){
        var user={correo:document.getElementById("correo").value,contraseña:document.getElementById("contraseña").value}
        axios.post(url+"/acceso",user).then(res =>{
            if (res.data==="si"){
            window.location.href="ingresar.html";
            }
        })
    }
    function ingreso(){
        axios.get(url+"/autorizado").then(res =>{
        document.getElementById("mensaje").innerHTML=res.data;
        })
    }
    return {
        acceso: acceso,
        ingreso: ingreso
    }
}) ();