<#import "spring.ftl" as spring>
<#macro layout>
    <!doctype html>
    <html lang="en" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <meta name="_csrf_token" content="${_csrf.token}"/>
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Main page</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@400;500;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="/style.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
                crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
                integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
                crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
                integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
                crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
                crossorigin="anonymous"></script>
    </head>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUserEmail = SPRING_SECURITY_CONTEXT.authentication.principal>
        <#assign role = authorizedUserEmail.authorities[0]>
    </#if>
    <header>
        <nav class="navbar navbar-expand-md bg-dark " data-bs-theme="dark">
            <div class="container">
                <a class="navbar-brand" href="/"><@spring.message "exam"/></a>
                <button class="navbar-toggler text-light bg-orange btn-user ms-auto" type="button"
                        data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <i class="bi bi-list-nested"></i>
                </button>
                <button type="button"
                        class="btn btn-user bg-orange rounded-1 text-light order-md-1 ms-3"
                        <#if authorizedUserEmail??>
                            onclick="window.location.href='/profile'"
                        <#else>
                            onclick="window.location.href='/login'"
                        </#if>
                        data-bs-toggle="tooltip" data-bs-placement="left" title="Profile">
                    <i class="bi bi-person-circle"></i>
                </button>

                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav fw-medium mx-auto">
                        <#if authorizedUserEmail??>
                            <p class="text-light"><@spring.message "username"/>: ${authorizedUserEmail.username}</p>
                        <#else >
                            <li class="mx-2 nav-item">
                                <a class="nav-link" href="/register"><@spring.message 'register'/></a>
                            </li>
                        </#if>
                    </ul>
                </div>
                <div class="dropdown">
                    <button class="btn btn-secondary dropdown-toggle bg-orange" type="button" id="dropdownMenuButton7"
                            data-bs-toggle="dropdown" aria-expanded="false">
                        <@spring.message 'layout.change.lang'/>
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton7">
                        <li><a class="dropdown-item" href="?lang=ru"><@spring.message 'layout.russian' /></a></li>
                        <li><a class="dropdown-item" href="?lang=en"><@spring.message 'layout.english' /></a></li>
                    </ul>
                </div>
                <#if authorizedUserEmail??>
                    <form action="/logout" method="post">
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                               value="${(_csrf.token)!'--no-token--'}"/>
                        <button type="submit"
                                class="btn btn-user bg-orange rounded-1 text-light fs-5 ms-3"><@spring.message 'logout'/></button>
                    </form>
                </#if>
            </div>
        </nav>
    </header>

    <body style=" height: 100vh;">
    <div class=" py-5">
        <div class="container">
            <div id="balance"></div>
            <#nested spring >
        </div>
    </div>


    </body>

    <footer class="py-5" style="background-color: #5d5d5d;">
        <div class="container footer-container text-dark">
            <div class=" opacity-75">
                <a href="#" class="text-decoration-none"><@spring.message "agreement"/></a>
                <p>«Copyright © <@spring.message "title"/> 2023»</p>
            </div>

            <div class="numbers ms-lg-5 mb-2 ms-md-3 ps-3  ">
                <p>+7 495 120-32-14</p>
                <p>+7 495 120-32-15</p>
            </div>
        </div>
    </footer>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>

    <script>
        <#if authorizedUserEmail??>
        let balance = document.getElementById('balance')

        window.onload = () => {
            fetchBalance();
            setInterval(fetchBalance, 20000);
        };

        async function fetchBalance() {
            try {
                let response = await fetch(`api/balance/${authorizedUserEmail.username}`);
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                let data = await response.json();
                console.log(data);
                balance.innerText = `<@spring.message "balance"/>: ` + data;
            } catch (error) {
                console.error('There has been a problem with your fetch operation:', error);
            }
        }

        </#if>
    </script>
    </html>

</#macro>