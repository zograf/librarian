export default function UserRootLayout(props) {
    const handleLogout = () => { window.location.href = "/" }
    const handleRelocation = (route) => { window.location.href = route }
    
    return(
        <main>
            <div style={{height:'100vh'}}>
                <div className="w-100 standard-padding-xs center" style={{position:'fixed', display:'grid', gridTemplateColumns:'0.2fr 2fr 0.2fr', justifyContent:'end', backgroundColor:'rgba(var(--background), 50%)', backdropFilter:'blur(40px)'}}>
                    <p className="hero-title"><b>Librari</b>an</p>
                    <div className="flex justify-center gap-l">
                        <button className="text-button" onClick={() => { window.location.href = '/library' }}>My Library</button>
                        <button className="text-button" >Trending</button>
                        <button className="text-button" onClick={() => { window.location.href = '/preferences' }}>Preferences</button>
                        <button className="text-button" onClick={() => { window.location.href = '/recommend' }}>Recommend</button>
                        {/* <p>{window.location.href.includes('/lib') ? "Da" : "Ne"}</p> */}
                    </div>
                    <div className="flex center justify-end">
                        <button className="icon-button">
                            <span className="material-symbols-outlined icon" onClick={handleLogout}>logout</span>
                        </button>
                    </div>
                </div>
                <div className="w-100">
                    {props.children}
                </div>
            </div>
        </main>
    )
}