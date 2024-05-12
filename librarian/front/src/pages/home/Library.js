export default function LibraryPage() {
    const userId = localStorage.getItem("id")
    const token = localStorage.getItem("token")
    const email = localStorage.getItem("username")

    return(
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
    )
}