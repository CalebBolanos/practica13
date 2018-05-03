package com.example.alumno.hilos;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText entrada;
    private TextView salida;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entrada = (EditText) findViewById(R.id.entrada);
        salida = (TextView) findViewById(R.id.salida);
    }


    public void calcularOperacion(View view) {
        int n = Integer.parseInt(entrada.getText().toString());
        //salida.append(n +"! = ");
        //int res = factorial(n);
        //salida.append(res + "\n");
        new MiTarea().execute(n);
    }

    public int factorial(int n) {
        int res=1;
        for (int i=1; i<=n; i++){
            res*=i;
            SystemClock.sleep(1000);
        }

        return res;

    }

    public String serie(int numero){
        //1+1 = 2
        //2+1 = 3
        //2+3 = 5
        //5+3 = 8
        //8+5= 13
        int num1 = 0, num2 = 0;
        int res = 0;
        String resultados = "0"+"\n"+"1"+"\n"+"1"+"\n";
        for (int i = 1; i < numero; i++) {
            if(i == 1){
                num1 = i+i;
                num2 = 0;
                resultados += num1+"\n";
            }
            else{
                if(i == 2){
                    num2 = num1 + 1;
                    resultados += num2+"\n";
                }
                else {
                    res = num1 + num2;
                    resultados += res+"\n";

                    if(i%2 ==0){
                        num2 = res;
                    }
                    else {
                        num1 = res;
                    }

                }
            }




        }
        return resultados;
    }

    class MiThread extends Thread {
        private int n, res;
        String res2 = "";
        public MiThread(int n) {
            this.n = n;
        }

        @Override
        public void run() {

            //salida.append(res2);
        }

//        @Override
//        public void run() {
//            res = factorial(n);
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    salida.append(res + "\n");
//                }
//            });
//        }
    }

//    public void calcularOperacion(View view) {
//        int n = Integer.parseInt(entrada.getText().toString());
//        salida.append(n + "! = ");
//        MiThread thread = new MiThread(n);
//        thread.start();
//    }


//    ejemplo AsyncTask
//    class MiTarea extends AsyncTask<Integer, Void, Integer> {
//
//        @Override
//
//        protected Integer doInBackground(Integer... n) {
//
//            return factorial(n[0]);
//
//        }
//
//        @Override
//
//        protected void onPostExecute(Integer res) {
//
//            salida.append(res + "\n");
//
//        }
//
//    }

//        public void calcularOperacion(View view) {
//        int n = Integer.parseInt(entrada.getText().toString());
//        salida.append(n + "! = ");
//        MiTarea tarea = new MiTarea();
//        tarea.execute(n);
//
//        }

    //    ejemplo AsyncTask whit progressdialog
//    class MiTarea extends AsyncTask<Integer, Integer, Integer> {
//
//        private ProgressDialog progreso;
//
//        @Override protected void onPreExecute() {
//
//            progreso = new ProgressDialog(MainActivity.this);
//
//            progreso.setProgressStyle(ProgressDialog.
//                    STYLE_HORIZONTAL);
//
//            progreso.setMessage("Calculando...");
//
//            progreso.setCancelable(false);
//
//            progreso.setMax(100);
//
//            progreso.setProgress(0);
//
//            progreso.show();
//
//        }
//
//        @Override protected Integer doInBackground(Integer... n) {
//
//            int res = 1;
//
//            for (int i = 1; i <= n[0]; i++) {
//
//                res *= i;
//
//                SystemClock.sleep(1000);
//
//                publishProgress(i*100 / n[0]);
//
//            }
//
//            return res;
//
//        }
//
//        @Override protected void onProgressUpdate(Integer... porc) {
//
//            progreso.setProgress(porc[0]);
//
//        }
//
//        @Override protected void onPostExecute(Integer res) {
//
//            progreso.dismiss();
//
//            salida.append(res + "\n");
//
//        }
//
//    }

    //    ejemplo AsyncTask whit progressdialog cancel
    class MiTarea extends AsyncTask<Integer, String, String> {

        private ProgressDialog progreso;

        @Override protected void onPreExecute() {

            progreso = new ProgressDialog(MainActivity.this);

            progreso.setProgressStyle(ProgressDialog.
                    STYLE_HORIZONTAL);

            progreso.setMessage("Calculando...");

            progreso.setCancelable(true);

            progreso.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {

                    MiTarea.this.cancel(true);

                }

            });

            progreso.setMax(100);

            progreso.setProgress(0);

            progreso.show();

        }

        @Override protected String doInBackground(Integer... n) {


            int res = 1;
            String res2 = "";
            for (int i = 1; i <= n[0] && !isCancelled(); i++) {

                res *= i;

                SystemClock.sleep(1000);

                publishProgress(""+(i*100 / n[0]));

            }
            res2 = serie(n[0]);
            res2 = res +" "+ res2;

            return res2;

        }

        @Override protected void onProgressUpdate(String... porc) {

            progreso.setProgress(Integer.parseInt(porc[0]));

        }

        @Override protected void onPostExecute(String res) {

            progreso.dismiss();

            salida.append(res);

        }

        @Override protected void onCancelled() {

            salida.append("cancelado\n");

        }

    }
}
