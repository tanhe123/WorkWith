package com.xiayule.workwithclient.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.xiayule.workwithclient.App;
import com.xiayule.workwithclient.data.GsonRequest;
import com.xiayule.workwithclient.data.RequestManager;
import com.xiayule.workwithclient.factory.ProgressDialogFactory;
import com.xiayule.workwithclient.model.Person;
import com.xiayule.workwithclient.model.Project;
import com.xiayule.workwithclient.model.Task;
import com.xiayule.workwithclient.util.Result;
import com.xiayule.workwithclient.util.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by storm on 14-3-25.
 */
public class WorkApi {
    private static final String BASE = "http://192.168.1.172:8080";

    public static final String HOST_PERSON = BASE + "/ajax/getPersonDo.action";
    public static final String HOST_UPDATE = BASE + "/ajax/updatePersonDo.action";
    public static final String HOST_LOGIN = BASE + "/ajax/loginDo.action";
    public static final String HOST_SEARCH_PROJECT= BASE + "/ajax/searchProject.action";

    public static void queryPerson(Context context, String username, String password, final OnApiEndListener okListener) {

        Map params = new HashMap();
        params.put("username", username);
        params.put("password", password);

        GsonRequest request = new GsonRequest(Request.Method.POST,
                WorkApi.HOST_PERSON,
                Person.PersonRequestData.class,
                params,
                new Response.Listener<Person.PersonRequestData>() {
                    @Override
                    public void onResponse(Person.PersonRequestData data) {

                        //TODO: 如果 data == null 怎么办?
                        if (data != null && data.getStatus().equals("ok")) {
                            // 保存数据
                            App.put(App.PERSON, data.getPerson());

                            okListener.onDo();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ToastUtils.showShort("Volley error");
                    }
                }
        );

        executeRequest(request, context);
    }

    public static void updateTask(Context context, Task task, final OnApiEndListener okListener) {
        // 设置参数
        HashMap param = new HashMap();
        param.put("method", "task");
        param.put("task", new Gson().toJson(task));

        final ProgressDialog progressDialog = ProgressDialogFactory.createProgressDialogWithSpinner(context,
                "更新中", "请稍候");

        // 显示 progressDialog
        progressDialog.show();

        // 更新 task
        GsonRequest req = new GsonRequest<Result>(Request.Method.POST, WorkApi.HOST_UPDATE, Result.class, param,
                new Response.Listener<Result>() {
                    @Override
                    public void onResponse(Result result) {
                        if (result.getStatus().equals("ok")) {
                            // 执行操作
                            okListener.onDo();

                            // 隐藏 progressdialog
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ToastUtils.showShort("volley error");
                        Log.d("TAG", "网络错误");

                        // 隐藏 progressdialog
                        progressDialog.dismiss();
                    }
                });

        executeRequest(req, context);
    }



    public static void updateProject(Context context, Project project, final OnApiEndListener okListener) {
        HashMap param = new HashMap();
        param.put("method", "project");
        param.put("project", new Gson().toJson(project));

        final ProgressDialog progressDialog = ProgressDialogFactory.createProgressDialogWithSpinner(context,
                "更新中", "请稍候");

        // 显示 progressDialog
        progressDialog.show();

        // 更新 task
        GsonRequest req = new GsonRequest<Result>(Request.Method.POST, WorkApi.HOST_UPDATE, Result.class, param,
                new Response.Listener<Result>() {
                    @Override
                    public void onResponse(Result result) {
                        if (result.getStatus().equals("ok")) {
                            okListener.onDo();

                            progressDialog.dismiss();
                        } else {
                            ToastUtils.showShort("发生错误");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ToastUtils.showShort("网络错误，请稍后重试");
                        Log.d("TAG", "网络错误");

                        // 隐藏 progressdialog
                        progressDialog.dismiss();
                    }
                });

        executeRequest(req, context);
    }

    public static void updatePerson(Context context, Person person, final OnApiEndListener okListener) {
        HashMap param = new HashMap();
        param.put("method", "person");
        param.put("person", new Gson().toJson(person));

        final ProgressDialog progressDialog = ProgressDialogFactory.createProgressDialogWithSpinner(context,
                "更新中", "请稍候");

        // 显示 progressDialog
        progressDialog.show();

        // 更新 task
        GsonRequest req = new GsonRequest<Result>(Request.Method.POST, WorkApi.HOST_UPDATE, Result.class, param,
                new Response.Listener<Result>() {
                    @Override
                    public void onResponse(Result result) {
                        if (result.getStatus().equals("ok")) {
                            okListener.onDo();

                            progressDialog.dismiss();
                        } else {
                            ToastUtils.showShort("发生错误");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ToastUtils.showShort("网络错误，请稍后重试");
                        Log.d("TAG", "网络错误");

                        // 隐藏 progressdialog
                        progressDialog.dismiss();
                    }
                });

        executeRequest(req, context);
    }

    public static void post(Context context, String url, Map params, String progressTitle, String progressMessage, final OnApiEndListenerWithResult okListener) {

        final ProgressDialog progressDialog = ProgressDialogFactory.createProgressDialogWithSpinner(context,
                progressTitle, progressMessage);

        // 显示 progressDialog
        progressDialog.show();

        // 更新 task
        GsonRequest req = new GsonRequest<Result>(Request.Method.POST, url, Result.class, params,
                new Response.Listener<Result>() {
                    @Override
                    public void onResponse(Result result) {
                            okListener.onDo(result);

                            progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ToastUtils.showShort("网络错误，请稍后重试");
                        Log.d("TAG", "网络错误");

                        // 隐藏 progressdialog
                        progressDialog.dismiss();
                    }
                });

        executeRequest(req, context);
    }

    public interface OnApiEndListener {
        public void onDo();
    }

    public interface OnApiEndListenerWithResult {
        public void onDo(Result result);
    }

    private static void executeRequest(Request<?> request, Context context) {
        RequestManager.addRequest(request, context);
    }

}
