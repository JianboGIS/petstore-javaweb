<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header_admin.jsp"%>

<div class="row container">
    <%@ include file="sidebar_admin.jsp"%>
    <%--控制器PetServlet中传递到视图的参数包含:pet categoryList category_id--%>
    <div class="col-9">
        <div class="card">
            <div class="card-header">
                <c:if test="${pet.id == ''}">
                    <h6>新增宠物</h6>
                </c:if>
                <c:if test="${pet.id != ''}">
                    <h6>编辑宠物</h6>
                </c:if>
            </div>
            <div class="card-body">
                <%--文件上传表单--%>
                <form id="uploadForm" method="post">
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">宠物图片</label>
                    <input type="file" id="file" name="file" class="col-sm-4">
                    <input type="button" id="uploadBtn" class="btn btn-primary col-sm-2" value="上传">
                </div>
                </form>
                <%-- 宠物数据提交表单 --%>
                <form action="${ctx}/EditPetServlet" method="post">
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label">分类</label>
                        <div class="col-sm-10">
                            <select class="form-control" id="category_id" name="category_id">
                            <c:forEach items="${categoryList}" var="category">
                                <c:if test="${category.id == category_id}">
                                    <option value="${category.id}" selected="selected">${category.name}</option>
                                </c:if>
                                <c:if test="${category.id != category_id}">
                                    <option value="${category.id}">${category.name}</option>
                                </c:if>
                            </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label">名称</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="title" value="${pet.title}" >
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label">特点</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="tag" value="${pet.tag}" >
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label">价格</label>
                        <div class="col-sm-10">
                            <input type="number" class="form-control" name="price" value="${pet.price}" >
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label">库存</label>
                        <div class="col-sm-10">
                            <input type="number" class="form-control" name="stock" value="${pet.stock}" >
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label">简介</label>
                        <div class="col-sm-8">
                            <textarea name="descs" class="form-control" rows="4" >${pet.descs}</textarea>
                        </div>
                        <div class="col-sm-2">
                            <img src="${ctx}/peting/${pet.photo}" id="img" alt="图片预览" width="80" height="100">
                        </div>
                    </div>

                    <div class="form-group offset-4 col-4">
                        <input type="hidden" name="id" value="${pet.id}">
                        <input type="hidden" id="photo" name="photo" value="${pet.photo}">
                        <input type="submit" class="btn btn-primary form-group col-12" value="保存">
                    </div>
                </form>
            </div>
            <div class="card-footer text-center">
                <span class="text-danger">${msg}</span>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer_admin.jsp"%>
<script>
    //使用Ajax请求上传宠物图片，页面不刷新
    $(document).ready(function (){
        $('#uploadBtn').click(function (){
            let formData = new FormData($('#uploadForm')[0]);//获取封装表单数据
            $.ajax({
                url: '${ctx}/FileUploadServlet', //表单提交url
                type: 'post',                    //表单提交方式
                data: formData,                  //表单提交数据
                contentType: false,              //文件上传时需设置contentType=false
                processData: false,              //文件上传时需设置processData=false
                success: function (returnData){  //returnData是FileUploadServlet返回的字符串，内容为宠物图片的新文件名
                    $('span.text-danger').text("宠物图片上传成功");      //显示提示信息
                    $('#img').prop('src','${ctx}/peting/'+returnData); //预览上传图片
                    $('#photo').val(returnData);                       //表单隐藏元素photo的值设置为新文件名
                },error:function (returnData){
                    $('span.text-danger').text("宠物图片上传失败");
                }
            });
        })
    })
</script>