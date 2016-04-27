function iniciaRelogio() {
 	var hoje = new Date();
 	var hora = hoje.getHours();
 	var minuto = hoje.getMinutes();
 	var segundo = hoje.getSeconds();
    
    // adiciona um zero na frente dos números menores que 10
    minuto = checaTempoMenorQueZero(minuto);
    segundo = checaTempoMenorQueZero(segundo);
    
    $("#relogio").html(hora + ":" + minuto + ":" + segundo);
    
    setTimeout("iniciaRelogio()",500);
}

function checaTempoMenorQueZero(i){
	if (i < 10) {
		i = "0" + i;
	}
	return i;
}

// O mesmo que $(document).ready(iniciaRelogio); ou $(function(){iniciaRelogio();});
$(window).load(iniciaRelogio());

