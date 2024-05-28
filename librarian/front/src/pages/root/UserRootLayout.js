import { useEffect, useState } from "react"

export default function UserRootLayout(props) {

    const handleLogout = () => { window.location.href = "/" }
    const [isAdmin, setIsAdmin] = useState(false)
    useEffect(() => { 
        console.log(localStorage.getItem("isAdmin"));
        setIsAdmin(localStorage.getItem("isAdmin") == 'true') 
    }, [])

    return(
        <main>
            <div style={{height:'100vh'}}>
                <div className="w-100 standard-padding-xs center" style={{position:'fixed', display:'grid', gridTemplateColumns:'0.2fr 2fr 0.2fr', justifyContent:'end', backgroundColor:'rgba(var(--background), 50%)', backdropFilter:'blur(40px)', zIndex:'10'}}>
                    <p className="hero-title"><b>Librari</b>an</p>
                    <div className="flex justify-center gap-l">
                        {!isAdmin && <button className={`text-button ${window.location.href.includes('/preferences') ? 'text-button-selected' : ''}`} onClick={() => { window.location.href = '/preferences' }}>Preferences</button>}
                        {!isAdmin && <button className={`text-button ${window.location.href.includes('/library') ? 'text-button-selected' : ''}`} onClick={() => { window.location.href = '/library' }}>My Library</button>}
                        {!isAdmin && <button className={`text-button ${window.location.href.includes('/trending') ? 'text-button-selected' : ''}`} onClick={() => { window.location.href = '/trending' }}>Trending</button>}
                        {!isAdmin && <button className={`text-button ${window.location.href.includes('/books/search') ? 'text-button-selected' : ''}`} onClick={() => { window.location.href = '/books/search' }}>Search</button>}
                        {!isAdmin && <button className={`text-button ${window.location.href.includes('/recommend') ? 'text-button-selected' : ''}`} onClick={() => { window.location.href = '/recommend' }}>Recommend</button>}
                        {isAdmin && <button className={`text-button ${window.location.href.includes('/admin/book/new') ? 'text-button-selected' : ''}`} onClick={() => { window.location.href = '/admin/book/new' }}>New Book</button>}
                    </div>
                    <div className="flex center justify-end">
                        <button className="icon-button">
                            <span className="material-symbols-outlined icon" onClick={handleLogout}>logout</span>
                        </button>
                    </div>
                </div>
                <div className="w-100" style={{paddingTop:"40px"}}>
                    {props.children}
                </div>
            </div>
        </main>
    )
}