<#import "../layout.ftlh" as main>
<@main.layout ; spring>

    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUserEmail = SPRING_SECURITY_CONTEXT.authentication.principal>
    </#if>
    <div class="row">
        <div class="col-md-8">
            <form action="/provider/${id}" method="get">
                <div class="d-flex justify-center">
                    <input class="form-control" type="search" aria-label="Search" name="recipient" data-search>
                    <button class="btn btn-success" type="submit">search</button></div>
            </form>
            <div class="card my-3">
                <div class="card-body">
                    <h5 class="card-title"><a href="/provider/${provider.id}" class="text-decoration-none text-dark">${provider.name}</a></h5>
                </div>
            </div>
            <#if recipient??>
                <div class="card my-3">
                    <div class="card-body">
                        <h5 class="card-title">${recipient.identifier}</h5>
                        <p class="text-end">${recipient.provider}</p>
                    </div>
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
                            <form action="/payment/provider/${provider.id}" method="post" enctype="application/x-www-form-urlencoded">
                                <div class="mb-3">
                                    <label for="payment" class="form-label">:</label>
                                    <@spring.formInput "paymentDto.balance" 'class="form-control" id="payment" name="balance" '/>
                                    <@spring.showErrors "<br>" "error text-danger"/>
                                    <label>
                                        <input type="text" hidden="hidden" value="${recipient.identifier}" name="recipient">
                                    </label>
                                </div>
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                       value="${(_csrf.token)!'--no-token--'}"/>
                                <button type="submit" class="btn btn-primary">pay</button>
                            </form>
                        <#else >
                        </#if>
                    </#if>
                </div>
            </div>
        </div>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>

    <script src="/js/main.js"></script>
</@main.layout>