export default function LibraryPage() {
    const userId = localStorage.getItem("id")
    const token = localStorage.getItem("token")
    const email = localStorage.getItem("username")

    const handleLogout = () => { window.location.href = "/" }

    return(
        <main>
            <div style={{height:'100vh'}}>
                <div className="w-100 standard-padding-xs center" style={{position:'fixed', display:'grid', gridTemplateColumns:'0.2fr 2fr 0.2fr', justifyContent:'end', backgroundColor:'rgba(var(--background), 50%)', backdropFilter:'blur(40px)'}}>
                    <p className="hero-title"><b>Librari</b>an</p>
                    <div className="flex justify-center gap-l">
                        <button className="text-button text-button-selected">My Library</button>
                        <button className="text-button">Trending</button>
                        <button className="text-button">Recommend</button>
                    </div>
                    <div className="flex center justify-end">
                        <button className="icon-button">
                            <span className="material-symbols-outlined icon" onClick={handleLogout}>logout</span>
                        </button>
                    </div>
                </div>
                <div className="w-100 standard-padding gap-s" style={{display:'grid', gridTemplateColumns:'1fr 1fr 1fr 1fr 1fr 1fr', paddingTop:'88px'}}>
                    <p className="card" style={{width:'100%', height:'400px', textAlign:'center'}}></p>
                    <p className="card" style={{width:'100%', height:'400px', textAlign:'center'}}></p>
                    <p className="card" style={{width:'100%', height:'400px', textAlign:'center'}}></p>
                    <p className="card" style={{width:'100%', height:'400px', textAlign:'center'}}></p>
                    <p className="card" style={{width:'100%', height:'400px', textAlign:'center'}}></p>
                    <p className="card" style={{width:'100%', height:'400px', textAlign:'center'}}></p>
                    <p className="card" style={{width:'100%', height:'400px', textAlign:'center'}}></p>
                    <p className="card" style={{width:'100%', height:'400px', textAlign:'center'}}></p>
                    <p className="card" style={{width:'100%', height:'400px', textAlign:'center'}}></p>
                    <p className="card" style={{width:'100%', height:'400px', textAlign:'center'}}></p>
                    <p className="card" style={{width:'100%', height:'400px', textAlign:'center'}}></p>
                    <p className="card" style={{width:'100%', height:'400px', textAlign:'center'}}></p>
                    <p className="card" style={{width:'100%', height:'400px', textAlign:'center'}}></p>
                    <p className="card" style={{width:'100%', height:'400px', textAlign:'center'}}></p>
                    <p className="card" style={{width:'100%', height:'400px', textAlign:'center'}}></p>
                </div>

            </div>
        </main>
    )
}