<#import "../layout.ftlh" as main>
<@main.layout ; spring>

    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUserEmail = SPRING_SECURITY_CONTEXT.authentication.principal>
    </#if>
    <div class="row">
        <div class="col-md-8">
            <form action="/" method="get">
                <div class="d-flex justify-center">
                <input class="form-control" type="search" aria-label="Search" name="recipient" data-search>
                <button class="btn btn-success" type="submit"><@spring.message "search"/></button></div>
            </form>
            <#if recipient??>
                <div class="card my-3">
                    <div class="card-body">
                        <h5 class="card-title">${recipient.uuid}</h5>
                        <p class="text-end">${recipient.name} ${recipient.surname}</p>
                    </div>
                </div>
                <#else>
                    <#list providers.content as provider>
                        <div class="card my-3">
                            <div class="card-body">
                                <h5 class="card-title"><a href="/provider/${provider.id}"
                                                          class="text-decoration-none text-dark">${provider.name}</a>
                                </h5>
                                <p><@spring.message "commission"/>: ${provider.commission}</p>
                            </div>
                        </div>
                    </#list>
                    <div class="my-2" id="pagination-profile">
                        <nav class="mb-2" aria-label="Page navigation example" id="applicant-pageButtons">
                            <ul class="pagination">
                                <#if page = 0>
                                    <li class="page-item"><a class="page-link" href="/?page=${page}">&laquo;</a></li>
                                <#else >
                                    <li class="page-item"><a class="page-link" href="/?page=${page-1}">&laquo;</a></li>
                                </#if>
                                <li class="page-item page-link">${page}</li>
                                <li class="page-item"><a class="page-link" href="/?page=${page + 1}">&raquo;</a></li>
                            </ul>
                        </nav>
                    </div>
            </#if>
            <nav aria-label="Page navigation example" id="paging-buttons">
            </nav>
        </div>
        <div class="col-md-4 h-100">
            <div class="card ">
                <div class="card-body">
                    <#if authorizedUserEmail??>
                        <#if recipient??>
                            <form action="/payment" method="post" enctype="application/x-www-form-urlencoded">
                                <div class="mb-3">
                                    <label for="payment" class="form-label">:</label>
                                    <@spring.formInput "paymentDto.balance" 'class="form-control" id="payment" name="balance" '/>
                                    <@spring.showErrors "<br>" "error text-danger"/>
                                    <label>
                                        <input type="text" hidden="hidden" value="${recipient.email}" name="recipient">
                                    </label>
                                </div>
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                       value="${(_csrf.token)!'--no-token--'}"/>
                                <button type="submit" class="btn btn-primary"><@spring.message "pay"/></button>
                            </form>
                            <#else >
                        </#if>
                    <#else >
                        <#if recipient??>
                        <form action="/payment/anon" method="post" enctype="application/x-www-form-urlencoded">
                            <div class="mb-3">
                                <label for="payment" class="form-label">:</label>
                                <@spring.formInput "paymentDto.balance" 'class="form-control" id="payment" name="balance" '/>
                                <@spring.showErrors "<br>" "error text-danger"/>
                                <label>
                                    <input type="text" hidden="hidden" value="${recipient.email}" name="recipient">
                                </label>
                            </div>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                   value="${(_csrf.token)!'--no-token--'}"/>
                            <button type="submit" class="btn btn-primary"><@spring.message "pay"/></button>
                        </form>
                        </#if>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</@main.layout>