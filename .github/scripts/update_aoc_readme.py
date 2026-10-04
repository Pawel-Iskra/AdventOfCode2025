import os
import re
import urllib.request

from datetime import datetime, timedelta
from zoneinfo import ZoneInfo

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



POLAND = ZoneInfo("Europe/Warsaw")
AOC_TZ = ZoneInfo("America/New_York")
aoc_start = datetime(2025, 12, day, 0, 0, 0, tzinfo=AOC_TZ)

completed_at = aoc_start + timedelta(
    hours=hours,
    minutes=minutes,
    seconds=seconds
)

completed_at = completed_at.astimezone(POLAND)