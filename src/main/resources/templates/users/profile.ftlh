<#import "../layout.ftlh" as main>
<@main.layout>


    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-scrollable">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Отклики</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <table class="table">
                        <thead>
                        <tr class="table-active">
                            <th scope="col">Resume</th>
                            <th scope="col">Vacancy</th>
                            <th scope="col">Chat</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list respondents as respondent>
                            <tr>
                                <td>
                                    <a class="nav-link text-decoration-none text-dark"
                                       href="/resume/${respondent.resume.id}">${respondent.resume.name}</a>
                                </td>
                                <th>
                                    <a class="nav-link text-decoration-none text-dark"
                                       href="/vacancies/info/${respondent.vacancy.id}">${respondent.vacancy.name}</a>
                                </th>
                                <td>
                                    <button type="button" class="btn btn-success"
                                            onclick="window.location.href='/chat/${respondent.id}'">
                                        write to employer
                                    </button>
                                </td>
                            </tr>
                        </#list>

                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>










    <div class="container my-5 mx-auto" style="max-width: 1100px; background: white; border-radius: 15px">
    <div class="row g-2 my-3 py-3">
        <div class="col-md-3">
            <div class="card border-0">
                <#if user.avatar??>
                    <img src="api/users/image/${user.avatar}" class="avatar-img" alt="User image" style="border-radius: 30%; width: 300px;
                height: 250px;
                object-fit: cover;
                cursor: pointer;
                margin: 0 auto;
                transition: transform 0.2s ease-in-out; ">
                <#else >
                    <img src="api/users/image/anon.jpeg" class="avatar-img" alt="User image" style="border-radius: 30%; width: 300px;
                height: 250px;
                object-fit: cover;
                cursor: pointer;
                margin: 0 auto;
                transition: transform 0.2s ease-in-out; ">
                </#if>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card border-0 ps-3">
                <h5 class="card-title">${user.name} ${user.surname}</h5>
                <p class="card-text">${user.age}</p>
                <div class="dropend">
                    <button class="btn btn-success dropdown-toggle" type="button" id="dropdownMenuButton1"
                            data-bs-toggle="dropdown" aria-expanded="false">
                        Edit
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                        <li><a class="dropdown-item" href="/applicant/edit">Edit profile</a></li>
                        <li><a class="dropdown-item" href="/applicant/changePassword">Change password</a></li>
                        <li><a class="dropdown-item" href="/applicant/uploadImage">Set a new image</a></li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="col-md">
            <div class="card border-0 align-items-center text-align-center">
                <a href="/resume/create" class="btn btn-danger res-btn text-decoration-none">+ Создать
                    резюме</a>
            </div>
        </div>
        <div class="col-md">
            <div class="card border-0 align-items-center">
                <button class="btn btn-success res-btn text-decoration-none text-center" data-bs-toggle="modal"
                        data-bs-target="#exampleModal">Отклики
                </button>
                <#if respondents??>
                    <span class="badge bg-warning rounded-pill position-absolute top-0 end-0 mt-5 me-2">${respondents?size}</span>
                </#if>
            </div>
        </div>

        <#if resumes?has_content>
            <h6 class="mt-5">резюме :</h6>
            <div class="row row-cols-1 row-cols-md-3 g-4">
                <#list resumes.content as resume>
                    <div class="col mb-3">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title"><a href="/resume/${resume.id}"
                                                          class="text-decoration-none text-dark">${resume.name}</a>
                                </h5>
                                <hr>
                                <p class="card-text">Last update: <span id="${resume.id}_last-update">${resume.updateDate}</span></p>
                            </div>
                        </div>
                        <div class="card-footer d-flex justify-content-between gx-2 my-2">
                            <div>
                                <button onclick="updateResume(${resume.id})" class="btn btn-primary" style="width: 150px">update
                                </button>
                            </div>

                            <div>
                                <button type="button" class="btn btn-success " style="width: 150px"
                                        onclick="window.location.href='/resume/edit/${resume.id}'">
                                    Edit
                                </button>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </#if>
        <div class="mb-2" id="pagination-profile">
            <nav class="mb-2" aria-label="Page navigation example" id="applicant-pageButtons">
                <ul class="pagination">
                    <li class="page-item"><a class="page-link" href="/profile?page=${page - 1}">Previous</a></li>
                    <li class="page-item page-link">${page}</li>
                    <li class="page-item"><a class="page-link" href="/profile?page=${page + 1}">Next</a></li>
                </ul>
            </nav>
        </div>
    </div>

    <script>
        const csrfToken = document.querySelector("meta[name='_csrf_token']").content;
        const csrfHeader = document.querySelector("meta[name='_csrf_header']").content;
        const headers = {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        };

        async function updateResume(id) {
            const lastUpdateElement = document.getElementById(id+'_last-update');
            try {
                const response = await fetch(`/api/resumes/update/`+id, {
                    method: 'PUT',
                    headers: headers
                });

                if (response.ok) {
                    const resume = await response.json();
                    lastUpdateElement.textContent = resume.updateDate;
                } else {
                    console.error('Failed to update resume');
                }
            } catch (error) {
                console.error('Error updating resume:', error);
            }
        }
    </script>
</@main.layout>