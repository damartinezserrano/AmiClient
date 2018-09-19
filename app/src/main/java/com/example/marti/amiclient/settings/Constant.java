package com.example.marti.amiclient.settings;

import com.example.marti.amiclient.estructura.contrato.Contrato;

/**
 * Created by Marti on 12/08/18.
 */

public class Constant {

    public static final String PREFERENCE_RANDOM = "codigo_verificacion_pref";

    public static final String RANDOM_CODE="codigo_aleatorio";

    public static boolean formularioOtros = false;

    public static boolean servicioAltaPrioridad = false;

    public static String SLASH="/";

    public static String ID="";

    public static String TOKEN="";

    public static String AUTH="Basic QW1pQXBwQWRtaW5pc3RyYWRvcjoqQW1pQWRtaW5BcHAyMDE4Kg==";

    public static String NRO_CONTRATO_SELECCIONADO="";




    public static String DIRECCION_ACTUAL_GOOGLE="";
    //formulario

    public static  String beneficiarios="";
    public static  String telefono="";
    public static  String sintomas="";
    public static  String direccion="";
    public static  int consulta_motivo_pos=0;
    public static  int ciudad_pos=0;

    // servicios web

    public static  String HTTP_DOMAIN="http://190.242.112.163";

    public static  String APP_PATH="/amisoft/newamisoft/index.php/app";

    public static  String ENDPOINT_USUARIO="/usuario";

    public static  String VALIDAR_USUARIO="/validar_usuario";

    public static  String SOLICITAR_SERVICIO="/solicitar_servicio";

    public static  String SOLICITAR_LLAMADA="/solicitar_llamada";

    public static  String LISTAR_CONTRATO="/listar_contrato";

    public static  String CONSULTAR_DATOS_PERSONALES="/consultar_datos_personales";



    // servicios web nativapps

    public static  String HTTP_DOMAIN_DVD="http://35.237.175.163:3000";

    public static  String END_POINT_MOTIV="/reasons";

    public static  String END_POINT_CIUDAD="/cities";

    public static  String END_POINT_LINEAS="/lines";



    //lista contratos estatica

    public static Contrato[] slistaContratos;


}
