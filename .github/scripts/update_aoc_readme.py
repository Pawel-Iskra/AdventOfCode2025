import os
import urllib.request

session = os.environ["AOC_SESSION"]

print("Session length:", len(session))
print("Contains whitespace:", any(c.isspace() for c in session))

year = 2025
url = f"https://adventofcode.com/{year}/leaderboard/self"

request = urllib.request.Request(
    url,
    headers={
        "Cookie": f"session={session}",
        "User-Agent": "AoC README updater"
    }
)

with urllib.request.urlopen(request) as response:
    html = response.read().decode("utf-8")

print("Status:", response.status)
print("URL:", response.url)
print("Content-Type:", response.headers.get("Content-Type"))
print(html[:300])