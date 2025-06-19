package br.com.amplitude.catalog.admin.application;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN anIn);
}