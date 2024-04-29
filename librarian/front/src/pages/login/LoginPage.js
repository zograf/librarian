import React, { useState, useMemo } from "react";

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
    
    const handleSubmit = (e) => {
        e.preventDefault()
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
