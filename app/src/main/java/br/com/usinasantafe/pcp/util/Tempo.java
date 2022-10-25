package br.com.usinasantafe.pcp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.usinasantafe.pcp.control.ConfigCTR;
import br.com.usinasantafe.pcp.model.bean.variaveis.ConfigBean;
import br.com.usinasantafe.pcp.model.dao.LogErroDAO;

public class Tempo {

    private static Tempo instance = null;
	
	public Tempo() {
	}
	
    public static Tempo getInstance() {
        if (instance == null)
        instance = new Tempo();
        return instance;
    }

    private String dthrAtualBaseString(){
        Date dataHora = new Date();
        return dthrLongToString(dataHora.getTime());
    }

    //////////////////////////////////// DATA/HORA ATUAL ///////////////////////////////////////////

    public String dthrAtualString(){
        return dthrLongToString(dthrAtualLong());
    }

    public String dtAtualString(){
        return dtLongToString(dthrAtualLong());
    }

    public Long dthrAtualLong(){
        return dthrStringToLong(dthrAtualBaseString());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////// STRING TO LONG ///////////////////////////////////////////

    public Long dthrStringToLong(String dthrString){
        Date date = new Date();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            date = format.parse(dthrString + ":00");
        } catch (ParseException e) {
            LogErroDAO.getInstance().insertLogErro(e);
        }
        return date.getTime();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////// LONG TO STRING ///////////////////////////////////////////

    public String dthrLongToString(Long dthrLong){
        Date dt = new Date (dthrLong);
        SimpleDateFormat df = new SimpleDateFormat ("dd/MM/yyyy HH:mm");
        return df.format(dt);
    }

    public String dtLongToString(Long dthrLong){
        Date dt = new Date (dthrLong);
        SimpleDateFormat df = new SimpleDateFormat ("dd/MM/yyyy");
        return df.format(dt);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public Long dthrAddMinutoLong(Long dthrLong, int minuto){
        dthrLong = dthrLong + (minuto * 60 * 1000);
        return dthrLong;
    }

    public boolean verDthrServ(String dthrServ){

        Long diaDif = (dthrStringToLong(dthrServ) - dthrAtualLong())/24/60/60/1000;

        if((diaDif >= 0) && (diaDif <= 15)){
            return true;
        }
        else{
            return false;
        }

    }

    public Long difDthr(int dia, int mes, int ano, int hora, int minute){

        String diaStr;
        if(dia < 10){
            diaStr = "0" + dia;
        }
        else{
            diaStr = String.valueOf(dia);
        }

        String mesStr;
        if(mes < 10){
            mesStr = "0" + mes;
        }
        else{
            mesStr = String.valueOf(mes);
        }

        String horasStr;
        if(hora < 10){
            horasStr = "0" + hora;
        }
        else{
            horasStr = String.valueOf(hora);
        }

        String minutosStr;
        if(minute < 10){
            minutosStr = "0" + minute;
        }
        else{
            minutosStr = String.valueOf(minute);
        }

        Long longDtDig =  dthrStringToLong(""+diaStr+"/"+mesStr+"/"+ano+" "+horasStr+":"+minutosStr);
        Long dif = longDtDig - dthrAtualLong();

        return dif;

    }

    public Long dthrLongDiaMenos(int dia){
        return dthrStringToLong(dthrAtualString()) - (dia*24*60*60*1000);
    }

}