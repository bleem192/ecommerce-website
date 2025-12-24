# 常见问题及解决方案

## 连接问题

**问题**：推送代码时遇到 "fatal: unable to access 'https://github.com/...': Could not resolve host: github.com"

**解决方案**：
1. 检查网络连接是否正常
2. 尝试使用 SSH URL 替代 HTTPS URL
3. 检查防火墙或代理设置

## 权限问题

**问题**：推送代码时遇到 "remote: Permission to user/repo.git denied to user2"

**解决方案**：
1. 确保您使用的是正确的 GitHub 账户
2. 检查 SSH 密钥是否已添加到 GitHub 账户
3. 对于 HTTPS，确保已正确输入用户名和密码或使用个人访问令牌

## 换行符问题

**问题**：推送代码时遇到 "warning: in the working copy of 'file', LF will be replaced by CRLF"

**解决方案**：
1. 这是 Windows 和 Unix 系统换行符差异导致的警告
2. 可以通过配置 Git 来统一换行符处理：
   ```bash
   git config --global core.autocrlf true  # Windows
   git config --global core.autocrlf input  # Linux/macOS
   ```

## 分支问题

**问题**：推送代码时遇到 "error: failed to push some refs to 'https://github.com/...'"

**解决方案**：
1. 确保本地分支与远程分支名称一致
2. 如果是首次推送，使用 `-u` 参数设置上游分支：
   ```bash
   git push -u origin main
   ```
3. 如果远程分支已存在且有新的提交，先拉取更新：
   ```bash
   git pull origin main
   ```

## 其他常见问题

**问题**：Git 命令执行缓慢

**解决方案**：
1. 检查网络连接
2. 清理 Git 缓存：
   ```bash
   git gc --auto
   ```

**问题**：忘记提交某个文件

**解决方案**：
1. 添加文件到暂存区：
   ```bash
   git add <file>
   ```
2. 提交更新：
   ```bash
   git commit -m "Add missing file"
   ```
3. 推送更新：
   ```bash
   git push origin main
   ```

**问题**：提交信息写错了

**解决方案**：
1. 修改最后一次提交的信息：
   ```bash
   git commit --amend -m "New commit message"
   ```
2. 强制推送更新：
   ```bash
   git push --force origin main
   ```

## 联系支持

如果遇到其他问题，可以：
1. 查看 GitHub 官方文档：https://docs.github.com/
2. 检查 Git 官方文档：https://git-scm.com/docs
3. 在 GitHub 社区寻求帮助：https://github.community/