<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <div class="container my-5 mx-auto" style="max-width: 1100px; background: white; border-radius: 15px">
    <div class="row g-2 my-3 py-3">

        <div class="col-md-6">
            <div class="card border-0 ps-3">
                <h5 class="card-title">${user.name} ${user.surname}</h5>
            </div>
        </div>

        <div class="col-md">
            <div class="card border-0 align-items-center text-align-center">
               #${user.uuid}
            </div>

            <div class="card border-0 align-items-center text-align-center">
                ${account.balance}$
            </div>
        </div>

        <table class="table">
            <thead>
            <tr class="table-active">
                <th scope="col">#</th>
                <th scope="col"><@spring.message 'sender'/></th>
                <th scope="col"><@spring.message 'recipient'/></th>
                <th scope="col"><@spring.message 'sumOfTransfer'/></th>
                <th scope="col"><@spring.message 'date'/></th>
            </tr>
            </thead>
            <tbody>

            <#if transfers??>
                <#list transfers as transfer>
                    <tr>
                        <td>
                            <p>${transfer?counter}</p>
                        </td>
                        <th>
                            <#if transfer.sender??>
                                <p>${transfer.sender}</p>
                            <#else >
                                <p><@spring.message "terminal"/></p>
                            </#if>
                        </th>
                        <td>
                            <#if transfer.provider??>
                                <p>${transfer.provider}</p>
                            <#elseif transfer.recipient??>
                                <p>${transfer.recipient}</p>
                                <#else>
                                <p>?</p>
                            </#if>
                        </td>
                        <td>
                            <p>${transfer.sumOfTransfer}</p>
                        </td>
                        <td>
                            <p>${transfer.transactionDate}</p>
                        </td>
                    </tr>
                </#list>
            </#if>

            </tbody>
        </table>
        <div class="mb-2" id="pagination-profile">
            <nav class="mb-2" aria-label="Page navigation example" id="applicant-pageButtons">
                <ul class="pagination">
                    <#if page = 0>
                        <li class="page-item"><a class="page-link" href="/profile?page=${page}">&laquo;</a></li>
                    <#else >
                        <li class="page-item"><a class="page-link" href="/profile?page=${page-1}">&laquo;</a></li>
                    </#if>
                    <li class="page-item page-link">${page}</li>
                    <li class="page-item"><a class="page-link" href="/profile?page=${page + 1}">&raquo;</a></li>
                </ul>
            </nav>
        </div>
    </div>
</@main.layout>