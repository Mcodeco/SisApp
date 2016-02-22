package com.sis.mcode.sisapp.communication;

import android.content.Context;
import android.util.Log;

//import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sis.mcode.sisapp.entity.InfoDetail;
import com.sis.mcode.sisapp.resources.AppProperties;
import com.sis.mcode.sisapp.service.impl.ConfigurationServiceImpl;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;

import com.sis.mcode.sisapp.R;
import com.sis.mcode.sisapp.util.MarshalDouble;

public class SoapServices {

    private String OPERATION_NAME = null;
    private String NAMESPACE = null;
    private Context _context;
    private Gson gson;
    private ConfigurationServiceImpl cfgService;
    MarshalDouble md;

    public SoapServices(Context ctx) {
        this._context = ctx;
        this.NAMESPACE = AppProperties.getProperty(ctx, "soap_namespace");
        this.gson = new Gson();
        this.cfgService = new ConfigurationServiceImpl();
        md = new MarshalDouble();
    }

    public DownloadListOfTipSegResult getTipSeg() {
        this.OPERATION_NAME = "ObtenerTiposDeSeguro";

        String soap_action = this.NAMESPACE.concat(this.OPERATION_NAME);
        SoapObject request = new SoapObject(this.NAMESPACE, this.OPERATION_NAME);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        String url = String.format("http://%s/wsSisApp/ServiceSisApp.asmx", cfgService.getConfiguration().getServer());

        HttpTransportSE httpTransport = new HttpTransportSE(url, 10000);
        try {
            httpTransport.call(soap_action, envelope);
            SoapObject soapObject = (SoapObject) envelope.getResponse();
            if (soapObject != null) {
                String success = (String) soapObject.getProperty("Success").toString();
                success = "\"success\":" + success;
                String str_result;
                if (success.contains("true")) {
                    String data = (String) soapObject.getProperty("Data").toString();
                    data = "\"data\":" + data;

                    str_result = "{" + success + "," + data + "}";
                } else {
                    str_result = "{" + success + "}";
                }
                Type type = new TypeToken<DownloadListOfTipSegResult>() {
                }.getType();
                DownloadListOfTipSegResult rslt = gson.fromJson(str_result, type);
                return rslt;
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            DownloadListOfTipSegResult rslt = new DownloadListOfTipSegResult();
            rslt.setErrorMessage("Error de conexi—n. Tiempo de espera agotado.");
            return rslt;
        } catch (IOException e) {
            e.printStackTrace();
            DownloadListOfTipSegResult rslt = new DownloadListOfTipSegResult();
            rslt.setErrorMessage(e.getMessage());
            return rslt;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            DownloadListOfTipSegResult rslt = new DownloadListOfTipSegResult();
            rslt.setErrorMessage(e.getMessage());
            return rslt;
        } catch (IllegalStateException | JsonSyntaxException e) {
            e.printStackTrace();
            DownloadListOfTipSegResult rslt = new DownloadListOfTipSegResult();
            rslt.setErrorMessage(e.getMessage());
            return rslt;
        }
        DownloadListOfTipSegResult rslt = new DownloadListOfTipSegResult();
        rslt.setErrorMessage("Ocurrió un error en la descarga de tipos de seguro");
        return rslt;
    }

    public DownloadListOfEessResult getEess(LatLng ll) {

        this.OPERATION_NAME = "ObtenerEstablecimientosPorUbicacion";

        String soap_action = this.NAMESPACE.concat(this.OPERATION_NAME);
        SoapObject request = new SoapObject(this.NAMESPACE, this.OPERATION_NAME);

        PropertyInfo x = new PropertyInfo();
        x.setName("dblX");
        x.setType(Double.class);
        x.setValue(ll.latitude);
        request.addProperty(x);

        PropertyInfo y = new PropertyInfo();
        y.setName("dblY");
        y.setType(Double.class);
        y.setValue(ll.longitude);
        request.addProperty(y);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        md.register(envelope);

        String url = String.format("http://%s/wsSisApp/ServiceSisApp.asmx", cfgService.getConfiguration().getServer());
        HttpTransportSE httpTransport = new HttpTransportSE(url, 10000);
        try {
            httpTransport.call(soap_action, envelope);
            SoapObject soapObject = (SoapObject) envelope.getResponse();

            if (soapObject != null) {
                String success = (String) soapObject.getProperty("Success").toString();
                success = "\"success\":" + success;
                String str_result;
                if (success.contains("true")) {
                    String data = (String) soapObject.getProperty("Data").toString();
                    data = "\"data\":" + data;
                    str_result = "{" + success + "," + data + "}";
                } else {
                    str_result = "{" + success + "}";
                }
                Type type = new TypeToken<DownloadListOfEessResult>() {
                }.getType();
                DownloadListOfEessResult rslt = gson.fromJson(str_result, type);
                return rslt;
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            DownloadListOfEessResult rslt = new DownloadListOfEessResult();
            rslt.setErrorMessage("Error de conexi—n. Tiempo de espera agotado.");
            return rslt;
        } catch (IOException e) {
            e.printStackTrace();
            DownloadListOfEessResult rslt = new DownloadListOfEessResult();
            rslt.setErrorMessage(e.getMessage());
            return rslt;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            DownloadListOfEessResult rslt = new DownloadListOfEessResult();
            rslt.setErrorMessage(e.getMessage());
            return rslt;
        } catch (IllegalStateException | JsonSyntaxException e) {
            e.printStackTrace();
            DownloadListOfEessResult rslt = new DownloadListOfEessResult();
            rslt.setErrorMessage(e.getMessage());
            return rslt;
        }
        DownloadListOfEessResult rslt = new DownloadListOfEessResult();
        rslt.setErrorMessage(_context.getResources().getString(R.string.msg_download_eess_failed));
        return rslt;
    }

    public DownloadListOfInfoResult getInfoByName(String firstLastname, String secondLastname, String firstName, String secondName) {
        this.OPERATION_NAME = "ObtenerAfiliadoxNom";

        String soap_action = this.NAMESPACE.concat(this.OPERATION_NAME);
        SoapObject request = new SoapObject(this.NAMESPACE, this.OPERATION_NAME);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("ApPat");
        p1.setType(String.class);
        p1.setValue(firstLastname);
        request.addProperty(p1);

        PropertyInfo p2 = new PropertyInfo();
        p2.setName("ApMat");
        p2.setType(String.class);
        p2.setValue(firstLastname);
        request.addProperty(p2);

        PropertyInfo p3 = new PropertyInfo();
        p3.setName("Nom1");
        p3.setType(String.class);
        p3.setValue(firstLastname);
        request.addProperty(p3);

        PropertyInfo p4 = new PropertyInfo();
        p4.setName("Nom2");
        p4.setType(String.class);
        p4.setValue(firstLastname);
        request.addProperty(p4);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        String url = String.format("http://%s/wsSisApp/ServiceSisApp.asmx", cfgService.getConfiguration().getServer());
        HttpTransportSE httpTransport = new HttpTransportSE(url, 10000);
        try {
            httpTransport.call(soap_action, envelope);
            SoapObject soapObject = (SoapObject) envelope.getResponse();
            if (soapObject != null) {
                String success = (String) soapObject.getProperty("Success").toString();
                success = "\"success\":" + success;
                String str_result;
                if (success.contains("true")) {
                    String data = (String) soapObject.getProperty("Data").toString();
                    data = "\"data\":" + data;

                    str_result = "{" + success + "," + data + "}";
                } else {
                    str_result = "{" + success + "}";
                }
                Type type = new TypeToken<DownloadListOfInfoResult>() {
                }.getType();
                DownloadListOfInfoResult rslt = gson.fromJson(str_result, type);
                return rslt;
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            DownloadListOfInfoResult rslt = new DownloadListOfInfoResult();
            rslt.setErrorMessage("Error de conexi—n. Tiempo de espera agotado.");
            return rslt;
        } catch (IOException e) {
            e.printStackTrace();
            DownloadListOfInfoResult rslt = new DownloadListOfInfoResult();
            rslt.setErrorMessage(e.getMessage());
            return rslt;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            DownloadListOfInfoResult rslt = new DownloadListOfInfoResult();
            rslt.setErrorMessage(e.getMessage());
            return rslt;
        } catch (IllegalStateException | JsonSyntaxException e) {
            e.printStackTrace();
            DownloadListOfInfoResult rslt = new DownloadListOfInfoResult();
            rslt.setErrorMessage(e.getMessage());
            return rslt;
        }
        DownloadListOfInfoResult rslt = new DownloadListOfInfoResult();
        rslt.setErrorMessage(_context.getResources().getString(R.string.msg_consulta_asegurado_failed));
        return rslt;
    }

    public DownloadListOfInfoResult getInfoByDocNumber(int typeDocument, String documentNumber) {
        this.OPERATION_NAME = "ObtenerAfiliadoxDoc";

        String soap_action = this.NAMESPACE.concat(this.OPERATION_NAME);
        SoapObject request = new SoapObject(this.NAMESPACE, this.OPERATION_NAME);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("TipDoc");
        p1.setType(Integer.class);
        p1.setValue(typeDocument);
        request.addProperty(p1);

        PropertyInfo p2 = new PropertyInfo();
        p2.setName("NumDoc");
        p2.setType(String.class);
        p2.setValue(documentNumber);
        request.addProperty(p2);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        String url = String.format("http://%s/wsSisApp/ServiceSisApp.asmx", cfgService.getConfiguration().getServer());
        HttpTransportSE httpTransport = new HttpTransportSE(url, 10000);
        try {
            httpTransport.call(soap_action, envelope);
            SoapObject soapObject = (SoapObject) envelope.getResponse();
            if (soapObject != null) {
                String success = (String) soapObject.getProperty("Success").toString();
                success = "\"success\":" + success;
                String str_result;
                if (success.contains("true")) {
                    String data = (String) soapObject.getProperty("Data").toString();
                    data = "\"data\":" + data;

                    str_result = "{" + success + "," + data + "}";
                } else {
                    str_result = "{" + success + "}";
                }
                Type type = new TypeToken<DownloadListOfInfoResult>(){}.getType();
                DownloadListOfInfoResult rslt = gson.fromJson(str_result, type);
                return rslt;
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            DownloadListOfInfoResult rslt = new DownloadListOfInfoResult();
            rslt.setErrorMessage("Error de conexi—n. Tiempo de espera agotado.");
            return rslt;
        } catch (IOException e) {
            e.printStackTrace();
            DownloadListOfInfoResult rslt = new DownloadListOfInfoResult();
            rslt.setErrorMessage(e.getMessage());
            return rslt;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            DownloadListOfInfoResult rslt = new DownloadListOfInfoResult();
            rslt.setErrorMessage(e.getMessage());
            return rslt;
        } catch (IllegalStateException | JsonSyntaxException e) {
            e.printStackTrace();
            DownloadListOfInfoResult rslt = new DownloadListOfInfoResult();
            rslt.setErrorMessage(e.getMessage());
            return rslt;
        }
        DownloadListOfInfoResult rslt = new DownloadListOfInfoResult();
        rslt.setErrorMessage(_context.getResources().getString(R.string.msg_consulta_asegurado_failed));
        return rslt;
    }

    public InfoDetail detail(String table, int numReg, String numberDocument) {
        this.OPERATION_NAME = "ObtenerAfiliadoDet";

        String soap_action = this.NAMESPACE.concat(this.OPERATION_NAME);
        SoapObject request = new SoapObject(this.NAMESPACE, this.OPERATION_NAME);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("CodTabla");
        p1.setType(String.class);
        p1.setValue(table);
        request.addProperty(p1);

        PropertyInfo p2 = new PropertyInfo();
        p2.setName("NumReg");
        p2.setType(Integer.class);
        p2.setValue(numReg);
        request.addProperty(p2);

        PropertyInfo p3 = new PropertyInfo();
        p3.setName("DocIdent");
        p3.setType(Integer.class);
        p3.setValue(numberDocument);
        request.addProperty(p3);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        String url = String.format("http://%s/wsSisApp/ServiceSisApp.asmx", cfgService.getConfiguration().getServer());
        HttpTransportSE httpTransport = new HttpTransportSE(url, 10000);
        try {
            httpTransport.call(soap_action, envelope);
            SoapObject soapObject = (SoapObject) envelope.getResponse();
            if (soapObject != null) {
                String data = (String) soapObject.getProperty("Data").toString();
                Type type = new TypeToken<InfoDetail>(){}.getType();
                InfoDetail rslt = gson.fromJson(data, type);
                return rslt;
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            InfoDetail rslt = new InfoDetail();
            rslt.setErrorMessage("Error de conexi—n. Tiempo de espera agotado.");
            return rslt;
        } catch (IOException e) {
            e.printStackTrace();
            InfoDetail rslt = new InfoDetail();
            rslt.setErrorMessage(e.getMessage());
            return rslt;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            InfoDetail rslt = new InfoDetail();
            rslt.setErrorMessage(e.getMessage());
            return rslt;
        } catch (IllegalStateException | JsonSyntaxException e) {
            e.printStackTrace();
            InfoDetail rslt = new InfoDetail();
            rslt.setErrorMessage(e.getMessage());
            return rslt;
        }
        InfoDetail rslt = new InfoDetail();
        rslt.setErrorMessage(_context.getResources().getString(R.string.msg_consulta_asegurado_failed));
        return rslt;
    }

}