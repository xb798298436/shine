package com.leadall.chauffeur.base;

import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by Javen on 2017/2/20.
 */
public class Functions {
    /**
     * ���в����ͷ���ֵ�� ����
     * @param <Result>
     * @param <Param>
     */
    public static abstract class FunctionWithParamAndResult<Result,Param> extends Function{
        public FunctionWithParamAndResult(String functionName) {
            super(functionName);
        }

        public abstract Result function(Param data);
    }

    /**
     * û�в����ͷ���ֵ�ķ���
     */
    public static abstract class Function{
        public String mFunctionName;
        public Function(String functionName){
            this.mFunctionName = functionName;
        }
    }

    /**
     * �з���ֵ��û�в����ķ���
     * @param <Result>
     */
    public static abstract class FunctionWithResult<Result> extends Function{
        public FunctionWithResult(String functionName) {
            super(functionName);
        }

        public abstract Result function();
    }

    /**
     * ���в���û�з���ֵ�ķ���
     * @param <Param>
     */
    public static abstract class  FunctionWithParam<Param> extends Function{
        public FunctionWithParam(String functionName) {
            super(functionName);
        }

        public abstract void function(Param param);
    }

    /**
     * û�в����ͷ���ֵ�ķ���
     */
    public static abstract class  FunctionNoParamAndResult extends Function{
        public FunctionNoParamAndResult(String functionName) {
            super(functionName);
        }

        public abstract void function();
    }

    private HashMap<String,FunctionWithParam> mFunctionWithParam ;
    private HashMap<String,FunctionWithResult> mFunctionWithResult ;
    private HashMap<String,FunctionNoParamAndResult> mFunctionNoParamAndResult ;
    private HashMap<String,FunctionWithParamAndResult> mFunctionWithParamAndResult ;


    /**
     * ��Ӵ������ĺ���
     * @param function {@link com.niu.myapp.myapp.view.util.Functions.FunctionWithParam}
     * @return
     */
    public Functions addFunction(FunctionWithParam function){
        if(function == null){
            return this;
        }
        if(mFunctionWithParam == null){
            mFunctionWithParam = new HashMap<>(1);
        }
        mFunctionWithParam.put(function.mFunctionName,function);

        return this;
    }

    /**
     * ��Ӵ�����ֵ�ĺ���
     * @param function {@link com.niu.myapp.myapp.view.util.Functions.FunctionWithResult}
     * @return
     */
    public Functions addFunction(FunctionNoParamAndResult function){
        if(function == null){
            return this;
        }
        if(mFunctionNoParamAndResult == null){
            mFunctionNoParamAndResult = new HashMap<>(1);
        }
        mFunctionNoParamAndResult.put(function.mFunctionName,function);

        return this;
    }

    /**
     * ��Ӽ��в������з���ֵ�ĺ���
     * @param function {@link com.niu.myapp.myapp.view.util.Functions.FunctionWithParamAndResult}
     * @return
     */
    public Functions addFunction(FunctionWithParamAndResult function){
        if(function == null){
            return this;
        }
        if(mFunctionWithParamAndResult == null){
            mFunctionWithParamAndResult = new HashMap<>(1);
        }
        mFunctionWithParamAndResult.put(function.mFunctionName,function);

        return this;
    }

    /**
     * ��Ӵ��з���ֵ�ĺ���
     * @param function
     * @return
     */
    public Functions addFunction(FunctionWithResult function){
        if(function == null){
            return this;
        }
        if(mFunctionWithResult == null){
            mFunctionWithResult = new HashMap<>(1);
        }
        mFunctionWithResult.put(function.mFunctionName,function);

        return this;
    }

    /**
     * ���ݺ��������ص��޲��޷���ֵ�ĺ���
     * @param funcName
     */
    public void invokeFunc(String funcName) throws FunctionException {
        FunctionNoParamAndResult f = null;
        if(mFunctionNoParamAndResult != null){
            f = mFunctionNoParamAndResult.get(funcName);
            if(f != null){
                f.function();
            }
        }

        if(f == null){
            throw new FunctionException("û�д˺���");

        }
    }

    /**
     * ���ݺ��������ص��޲��з���ֵ�ĺ���
     * @param funcName
     */
    public <Result> Result invokeFuncWithResult(String funcName, Class<Result> c) throws FunctionException {
        FunctionWithResult f = null;
        if(mFunctionWithResult != null){
            f = mFunctionWithResult.get(funcName);
            if(f != null){
                if(c != null){
                    return c.cast(f.function());
                }else{
                    return (Result)f.function();
                }

            }
        }

        if(f == null){
            throw new FunctionException("û�д˺���");
        }
        return null;
    }

    /**
     * ���þ��в����ĺ���
     * @param funcName
     * @param param
     * @param <Param>
     */
    public <Param> void invokeFunc(String funcName,Param param)throws FunctionException{
        FunctionWithParam f = null;
        if(mFunctionWithParam != null){
             f = mFunctionWithParam.get(funcName);
            if(f != null){
                f.function(param);
            }
        }


    }

    /**
     * ���þ��в�����ͬʱ���з���ֵ�ĺ���
     * @param funcName
     * @param param
     * @param <Result>
     * @param <Param>
     * @return
     */
    public <Result,Param> Result invokeFuncWithResult(String funcName,Param param,Class<Result> c) throws FunctionException {
        FunctionWithParamAndResult f = null;
        if(mFunctionWithParamAndResult != null){
            f = mFunctionWithParamAndResult.get(funcName);
            if(f != null){
                if(c != null){
                    return c.cast(f.function(param));
                }else{
                    return (Result)f.function(param);
                }
            }
        }

        if( f == null){
            throw new FunctionException("û�д˺���");

        }
        return null;
    }

    /**
     * �����Ĳ������������Ĳ����漰�����ֵʱ�������ô��࣬
     * ����ʹ�ù��򣺴������ȡ������˳�����һ�£����򱨴�
     */
    public static class FunctionParams {

        private Bundle mParams = new Bundle(1);
        private int mIndex = -1;
        private Map mObjectParams = new HashMap(1);

        FunctionParams(Bundle mParams,Map mObjectParams){
            this.mParams = mParams;
            this.mObjectParams = mObjectParams;
        }

        public <Param> Param getObject(Class<Param> p){
            if(mObjectParams == null){
                return null;
            }
            return p.cast(mObjectParams.get((mIndex++) + ""));
        }

        /**
         * ��ȡintֵ
         * @return
         */
        public int getInt(){
            if(mParams != null){
                return mParams.getInt((mIndex++) + "");
            }
            return 0;
        }

        /**
         * ��ȡintֵ
         * @param defalut
         * @return
         */
        public int getInt(int defalut){
            if(mParams != null){
                return mParams.getInt((mIndex++) + "");
            }
            return defalut;
        }

        /**
         * ��ȡ�ַ���
         * @param defalut
         * @return
         */
        public String getString(String defalut){
            if(mParams != null){
                return mParams.getString((mIndex++) + "");
            }
            return defalut;
        }

        /**
         * ��ȡ�ַ���
         * @return
         */
        public String getString(){
            if(mParams != null){
                return mParams.getString((mIndex++) + "");
            }
            return null;
        }



        /**
         * ��ȡBooleanֵ
         * @return Ĭ�Ϸ���false
         */
        public boolean getBoolean(){
            if(mParams != null){
                return mParams.getBoolean((mIndex++) + "");
            }
            return false;
        }

        /**
         * ��������������������
         */
        public static class FunctionParamsBuilder{
            private Bundle mParams ;
            private int mIndex = -1;
            private Map mObjectParams = new HashMap(1);

            public FunctionParamsBuilder(){

            }

            public FunctionParamsBuilder putInt(int value){
                if(mParams == null){
                    mParams = new Bundle(2);
                }
                mParams.putInt((mIndex++) + "", value);
                return this;
            }

            public FunctionParamsBuilder putString(String value){
                if(mParams == null){
                    mParams = new Bundle(2);
                }
                mParams.putString((mIndex++) + "", value);
                return this;
            }

            public FunctionParamsBuilder putBoolean(boolean value){
                if(mParams == null){
                    mParams = new Bundle(2);
                }
                mParams.putBoolean((mIndex++) + "", value);
                return this;
            }

            public  FunctionParamsBuilder putObject(Object value){

                if(mObjectParams == null){
                    mObjectParams = new HashMap(1);
                }
                mObjectParams.put((mIndex++) + "", value);
                return this;
            }

            public FunctionParams create(){
                FunctionParams instance = new FunctionParams(mParams,mObjectParams);
                return instance;
            }
        }

    }
    
}
