import React, { useState, useMemo } from "react";
import axios from "axios";
import { API } from "../../enviroment";

export function LoginPage() {
    return(
        <main className="fh-100 w-100 flex center justify-center">
            <LoginForm/>
        </main>
    )
}

function LoginForm() {

    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")

    const handleEmail = (e) => setEmail(e.target.value);
    const handlePassword = (e) => setPassword(e.target.value);
    
    useMemo(() => {
        localStorage.setItem("token", "")
        localStorage.setItem("username", "")
        localStorage.setItem("id", "")
        localStorage.setItem("isUser", false)
        localStorage.setItem("isAdmin", false)
    }, [])


    const handleSubmit = (e) => {
        e.preventDefault()
        let payload = {
            "email" : email,
            "password" : password
        }
        axios.post(API + "/user/login", payload)
            .then(resp => {
                console.log(resp);
                console.log("isAdmin:", resp.data.userRole == "ROLE_ADMIN");

                localStorage.setItem("token", resp.data.accessToken)
                localStorage.setItem("username", resp.data.email)
                localStorage.setItem("id", resp.data.userId)
                localStorage.setItem("isUser", resp.data.userRole == "ROLE_USER")
                localStorage.setItem("isAdmin", resp.data.userRole == "ROLE_ADMIN")

                if (resp.data.userRole == "ROLE_USER") window.location.href = '/library'
                else window.location.href = '/admin/book/new'

            })
            .catch(e => alert("Opsie - Login failed"))
    }

    return(
        <div style={{width: 'clamp(200px, 25%, 280px)'}}>
            <p className="flex center justify-center page-title">Welcome</p>
            <div className="input-wrapper regular-border v-spacer-xs">
                <span className="material-symbols-outlined icon input-icon">mail</span>
                <input placeholder="Email" type="email" value={email} onChange={handleEmail} />
            </div>
            <div className="input-wrapper regular-border v-spacer-xs">
                <span className="material-symbols-outlined icon input-icon">lock</span>
                <input placeholder="Password" type="Password" value={password} onChange={handlePassword}/>
            </div>
            <button className='solid-accent-button w-100 v-spacer-xs' onClick={handleSubmit}>Login</button>
            <button className='outline-button w-100' onClick={() => { window.location.href = "/register"}}>No Account?</button>
        </div>
    )

}
