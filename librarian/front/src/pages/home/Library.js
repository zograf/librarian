export default function LibraryPage() {
    const userId = localStorage.getItem("id")
    const token = localStorage.getItem("token")
    const email = localStorage.getItem("username")

    return(
        <main>
            <p>Yey! {email}</p>
        </main>
    )
}