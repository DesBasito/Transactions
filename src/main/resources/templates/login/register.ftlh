<#import "../layout.ftlh" as main>

<@main.layout ; spring>
    <div class="row fw-medium pt-2 justify-content-center align-items-center h-100">
        <div class="col-12 col-lg-9 col-xl-7">
            <div class="card shadow-2-strong card-registration" style="border-radius: 15px;">
                <div class="card-body py-3 px-4 p-md-5">
                    <h3 class="mb-4 pb-2 pb-md-0">Регистрация</h3>
                    <form action="/register" method="POST" id="form">
                        <div class="row">
                            <#assign errorClassList = "text-danger d-block error" />

                            <div class="col-12 mb-2">
                                <div id="emailHelp" class="form-text">После регистрации изменить
                                    адрес почты и тип аккаунта нельзя
                                </div>
                            </div>

                            <div class="col-md-6 mb-4 pb-2">
                                <label for="email">Адрес электронной почты
                                </label>
                                <@spring.formInput 'userCreationDto.email'
                                'id="email" class="form-control form-control-lg"' 'email' />
                                <@spring.showErrors '<br/>' '${errorClassList}' />
                            </div>

                            <div class="col-md-6 mb-4">
                                <label for="password">Придумайте пароль</label>
                                <@spring.formPasswordInput 'userCreationDto.password' 'id="password"
                                       class="form-control form-control-lg"' />
                                <@spring.showErrors '<br/>' '${errorClassList}' />
                            </div>

                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-4">
                                <label for="name" id="nameLabel">Имя</label>
                                <@spring.formInput 'userCreationDto.name' 'id="name"
                                       class="form-control form-control-lg"' />
                                <@spring.showErrors '<br/>' '${errorClassList}' />
                            </div>

                            <div class="col-md-6 mb-4 pb-2">
                                <label for="surname">Фамилия</label>
                                <@spring.formInput 'userCreationDto.surname' 'id="surname" class="form-control form-control-lg"' />
                                <@spring.showErrors '<br/>' '${errorClassList}' />
                            </div>
                        </div>

                        <div class="row mt-4 pt-2">
                            <div class="col">
                                <#if _csrf??>
                                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                           value="${(_csrf.token)!'--no-token--'}"/>
                                </#if>
                                <input type="hidden" name="role" value="USER" />
                                <button type="submit"
                                        class="btn btn-outline-primary fw-medium btn-lg">Зарегистрироваться</button>
                            </div>
                            <div class="col align-content-center">
                                <p class="mb-0">Уже зарегистрированы?
                                    <a href="/login" class="text-primary-50
                                            text-decoration-none fw-bold">Войти
                                    </a>
                                </p>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</@main.layout>